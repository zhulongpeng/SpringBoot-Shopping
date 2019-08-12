package com.zlp.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zlp.admin.dao.AdminPermissionRelationDao;
import com.zlp.admin.dao.UmsAdminRoleRelationDao;
import com.zlp.admin.dto.UmsAdminLoginParam;
import com.zlp.admin.dto.UmsAdminParam;
import com.zlp.admin.service.UmsAdminService;
import com.zlp.admin.util.JwtTokenUtil;
import com.zlp.common.api.Constant;
import com.zlp.common.util.XaUtil;
import com.zlp.mbg.mapper.UmsAdminLoginLogMapper;
import com.zlp.mbg.mapper.UmsAdminMapper;
import com.zlp.mbg.mapper.UmsAdminPermissionRelationMapper;
import com.zlp.mbg.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    @Autowired
    private UmsAdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UmsAdminLoginLogMapper loginLogMapper;

    @Autowired
    private UmsAdminPermissionRelationMapper adminPermissionRelationMapper;

    @Autowired
    private AdminPermissionRelationDao adminPermissionRelationDao;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setStatus(Constant.Status.valid);
        umsAdmin.setCreateTime(new Date());
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdmins = adminMapper.selectByExample(example);
        if(XaUtil.isNotEmpty(umsAdmins)) return null;
        String encode = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encode);
        adminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if(XaUtil.isEmpty(adminList)) return null;
        return adminList.get(0);
    }

    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        return adminRoleRelationDao.getPermissionList(adminId);
    }

    @Override
    public String login(UmsAdminLoginParam umsAdminLoginParam) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(umsAdminLoginParam.getUsername());
            if(!passwordEncoder.matches(umsAdminLoginParam.getPassword(), userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token = jwtTokenUtil.generateToken(userDetails);
            updateLoginTimeByUsername(umsAdminLoginParam.getUsername());
            insertLoginLog(umsAdminLoginParam.getUsername());
        } catch (UsernameNotFoundException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring(tokenHead.length());
        if(jwtTokenUtil.canRefresh(token)){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

    @Override
    public List<UmsAdmin> userList(String name, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UmsAdminExample example = new UmsAdminExample();
        UmsAdminExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(name)){
            criteria.andUsernameLike("%" + name + "%");
            example.or(example.createCriteria().andNickNameLike("%" + name + "%"));
        }
        return adminMapper.selectByExample(example);
    }

    @Override
    public int update(Long id, UmsAdmin umsAdmin) {
        umsAdmin.setId(id);
        umsAdmin.setPassword(null);
        return adminMapper.updateByPrimaryKeySelective(umsAdmin);
    }

    @Override
    public UmsAdmin getAdminById(Long id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateRoles(Long adminId, String roleIds) {
        List<Long> roles = Arrays.stream(roleIds.split(Constant.Separator.FH)).map(id -> Long.parseLong(id.trim())).collect(Collectors.toList());
        int count = roleIds == null ? 0 : roles.size();
        //先删除原来的

        return 0;
    }

    @Override
    public int delete(Long id) {
        return adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return adminRoleRelationDao.getRoleList(adminId);
    }

    @Override
    public int updatePermission(Long adminId, List<Long> permissionIds) {
        //删除原所有权限关系
        UmsAdminPermissionRelationExample relationExample = new UmsAdminPermissionRelationExample();
        relationExample.createCriteria().andAdminIdEqualTo(adminId);
        adminPermissionRelationMapper.deleteByExample(relationExample);
        //获取用户所有角色权限
        List<UmsPermission> permissionList = adminRoleRelationDao.getPermissionList(adminId);
        List<Long> rolePermissionList = permissionList.stream().map(UmsPermission::getId).collect(Collectors.toList());
        if(XaUtil.isNotEmpty(permissionIds)) {
            List<UmsAdminPermissionRelation> relationList = new ArrayList<>();
            //筛选出+权限
            List<Long> addPermissionIdList = permissionIds.stream().filter(permissionId -> !permissionList.contains(permissionId)).collect(Collectors.toList());
            //筛选出-权限
            List<Long> subPermissionIdList = rolePermissionList.stream().filter(permissionId -> !permissionIds.contains(permissionId)).collect(Collectors.toList());
            //插入+-权限关系
            relationList.addAll(convert(adminId, 1, addPermissionIdList));
            relationList.addAll(convert(adminId, -1, subPermissionIdList));
            return adminPermissionRelationDao.insertList(relationList);
        }
        return 0;
    }

    private List<UmsAdminPermissionRelation> convert(Long adminId, Integer type, List<Long> permissionIdList) {
        List<UmsAdminPermissionRelation> collect = permissionIdList.stream().map(permissionId -> {
            UmsAdminPermissionRelation relation = new UmsAdminPermissionRelation();
            relation.setAdminId(adminId);
            relation.setType(type);
            relation.setPermissionId(permissionId);
            return relation;
        }).collect(Collectors.toList());
        return collect;
    }

    private void insertLoginLog(String username) {
        UmsAdmin adminByUsername = getAdminByUsername(username);
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(adminByUsername.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
        loginLogMapper.insert(loginLog);
    }

    private void updateLoginTimeByUsername(String username) {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setLoginTime(new Date());
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        adminMapper.updateByExampleSelective(umsAdmin, example);
    }
}

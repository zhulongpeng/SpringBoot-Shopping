package com.zlp.common.api;

import java.util.Arrays;
import java.util.List;

public class Constant {

    public static class Status {

        public static final int locked = -1;// 锁定状态

        public static final int invalid = 0;// 无效/非正常状态

        public static final int valid = 1;// 有效/正常状态

        public static final int not_show = 2;// 不显示

        public static final int delete = 3;// 删除

        public static final List<Integer> validList = Arrays.asList(valid, not_show);
    }

    public static final class Code {

        public static final int success = 1;

        public static final int error = 0;
    }


    public static final class Message {

        public static final String success = "Success!";

        public static final String error = "System error!";

        public static final String object_not_find = "No Records Matched！";
    }

    public static final class Separator{

        public static final String DH = ",";

        public static final String FH = ";";

    }

}

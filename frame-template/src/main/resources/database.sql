-- 手动输入表单保存数据
create table DT_MANUAL_INPUT_TEMP_DATA
(
  ID                   VARCHAR2(30 char) not null
    constraint PK_DT_MANUAL_INPUT_TEMP_DATA
      primary key,
  BUSINESS_ID          VARCHAR2(30 char) not null,
  TEMPLATE_PRIMARY_KEY VARCHAR2(30 char),
  SPLIT_DATA_ONE       VARCHAR2(4000 char),
  SPLIT_DATA_TWO       VARCHAR2(4000 char),
  SPLIT_DATA_THERE     VARCHAR2(4000 char),
  SPLIT_DATA_FOUR      VARCHAR2(4000 char)
)
/

comment on table DT_MANUAL_INPUT_TEMP_DATA is '手动输入表单保存数据（用于回显和生成文件）（业务数据表）'
/

comment on column DT_MANUAL_INPUT_TEMP_DATA.ID is '主键'
/

comment on column DT_MANUAL_INPUT_TEMP_DATA.BUSINESS_ID is '字段名称'
/

comment on column DT_MANUAL_INPUT_TEMP_DATA.TEMPLATE_PRIMARY_KEY is '模板主键'
/

comment on column DT_MANUAL_INPUT_TEMP_DATA.SPLIT_DATA_ONE is '拆分字段1'
/

comment on column DT_MANUAL_INPUT_TEMP_DATA.SPLIT_DATA_TWO is '拆分字段2'
/

comment on column DT_MANUAL_INPUT_TEMP_DATA.SPLIT_DATA_THERE is '拆分字段3'
/

comment on column DT_MANUAL_INPUT_TEMP_DATA.SPLIT_DATA_FOUR is '拆分字段4'
/

-- 动态模板文件表
create table DT_DYNAMIC_FILE_TEMPLATE
(
  FILE_TEMPLATE_ID   VARCHAR2(30 char)  not null
    constraint PK_DT_DYNAMIC_FILE_TENPLATE
      primary key,
  FILE_ID            VARCHAR2(30 char)  not null,
  FILE_TYPE          VARCHAR2(30 char)  not null,
  FILE_EXTENSION     VARCHAR2(5 char),
  BUSINESS_TYPE      VARCHAR2(10 char),
  BUSINESS_PHASE     VARCHAR2(10 char),
  CREATE_TIME        TIMESTAMP(6),
  LAST_UPDATE_TIME   TIMESTAMP(6),
  FILE_TEMPLATE_NAME VARCHAR2(100 char) not null
)
/

comment on table DT_DYNAMIC_FILE_TEMPLATE is '动态模板文件表'
/

comment on column DT_DYNAMIC_FILE_TEMPLATE.FILE_TEMPLATE_ID is '模板文件ID'
/

comment on column DT_DYNAMIC_FILE_TEMPLATE.FILE_ID is '文件ID'
/

comment on column DT_DYNAMIC_FILE_TEMPLATE.FILE_TYPE is '文件类型'
/

comment on column DT_DYNAMIC_FILE_TEMPLATE.FILE_EXTENSION is '扩展名/文件格式，用于同一文件需要不同格式'
/

comment on column DT_DYNAMIC_FILE_TEMPLATE.BUSINESS_TYPE is '业务类型'
/

comment on column DT_DYNAMIC_FILE_TEMPLATE.BUSINESS_PHASE is '业务阶段'
/

comment on column DT_DYNAMIC_FILE_TEMPLATE.CREATE_TIME is '创建时间'
/

comment on column DT_DYNAMIC_FILE_TEMPLATE.LAST_UPDATE_TIME is '最后更新时间'
/

comment on column DT_DYNAMIC_FILE_TEMPLATE.FILE_TEMPLATE_NAME is '模板文件名称'
/

-- 手动输入字段枚举配置表
create table DT_MANUAL_INPUT_FIELD_ENUM
(
  ID                           VARCHAR2(20 char) not null
    constraint PK_DT_MANUAL_INPUT_FIELD_ENUM
      primary key,
  MANUAL_INPUT_FIELD_CONFIG_ID VARCHAR2(30 char) not null,
  ENUM_KEY                     VARCHAR2(50 char),
  ENUM_VALUE                   VARCHAR2(30 char),
  DISPLAY_ORDER                VARCHAR2(10 char)
)
/

comment on table DT_MANUAL_INPUT_FIELD_ENUM is '手动输入字段枚举配置表'
/

comment on column DT_MANUAL_INPUT_FIELD_ENUM.ID is '主键'
/

comment on column DT_MANUAL_INPUT_FIELD_ENUM.MANUAL_INPUT_FIELD_CONFIG_ID is '对应手动输入字段配置表中的主键'
/

comment on column DT_MANUAL_INPUT_FIELD_ENUM.ENUM_KEY is '枚举key'
/

comment on column DT_MANUAL_INPUT_FIELD_ENUM.ENUM_VALUE is '枚举值'
/

comment on column DT_MANUAL_INPUT_FIELD_ENUM.DISPLAY_ORDER is '页面展示顺序'
/

-- 手动输入字段配置表
create table DT_MANUAL_INPUT_FIELD_CONFIG
(
  MANUAL_INPUT_FIELD_CONFIG_ID   VARCHAR2(30 char) not null
    constraint PK_MANUAL_INPUT_FIELD_CONFIG
      primary key,
  FIELD_NAME                     VARCHAR2(30 char) not null,
  FIELD_VARIABLE_NAME            VARCHAR2(30 char),
  TEMPLATE_ID                    VARCHAR2(30 char),
  FIELD_TYPE                     VARCHAR2(10 char),
  QUERY_SQL                      VARCHAR2(1000 char),
  DEFAULT_VALUE                  VARCHAR2(300 char),
  RELATED_FIELD_ID               VARCHAR2(20 char),
  CHECK_REGEX                    VARCHAR2(100 char),
  IS_REQUIRED                    VARCHAR2(2 char),
  DISPLAY_ORDER                  VARCHAR2(20 char),
  REMARK                         VARCHAR2(400 char),
  FORMAT_RULE                    VARCHAR2(50 char),
  UPPERCASE_AMOUNT_VARIABLE_NAME VARCHAR2(30 char)
)
/

comment on table DT_MANUAL_INPUT_FIELD_CONFIG is '手动输入字段配置表'
/

comment on column DT_MANUAL_INPUT_FIELD_CONFIG.MANUAL_INPUT_FIELD_CONFIG_ID is '主键'
/

comment on column DT_MANUAL_INPUT_FIELD_CONFIG.FIELD_NAME is '字段名称'
/

comment on column DT_MANUAL_INPUT_FIELD_CONFIG.FIELD_VARIABLE_NAME is '字段变量名（sql中#{}中的内容）'
/

comment on column DT_MANUAL_INPUT_FIELD_CONFIG.TEMPLATE_ID is '模板主键'
/

comment on column DT_MANUAL_INPUT_FIELD_CONFIG.FIELD_TYPE is '字段类型(日期 0、下拉框包括枚举1、输入框 2,下拉框查询sql 3)'
/

comment on column DT_MANUAL_INPUT_FIELD_CONFIG.QUERY_SQL is '查询sql'
/

comment on column DT_MANUAL_INPUT_FIELD_CONFIG.DEFAULT_VALUE is '默认值（下拉框、日期、输入框）'
/

comment on column DT_MANUAL_INPUT_FIELD_CONFIG.RELATED_FIELD_ID is '联动字段主键'
/

comment on column DT_MANUAL_INPUT_FIELD_CONFIG.CHECK_REGEX is '校验正则'
/

comment on column DT_MANUAL_INPUT_FIELD_CONFIG.IS_REQUIRED is '是否必填（1:是  0:否）'
/

comment on column DT_MANUAL_INPUT_FIELD_CONFIG.DISPLAY_ORDER is '页面展示顺序'
/

comment on column DT_MANUAL_INPUT_FIELD_CONFIG.REMARK is '备注'
/

comment on column DT_MANUAL_INPUT_FIELD_CONFIG.FORMAT_RULE is '格式化规则'
/

comment on column DT_MANUAL_INPUT_FIELD_CONFIG.UPPERCASE_AMOUNT_VARIABLE_NAME is '金额转大写对应属性'
/

-- 业务数据回显填充
create table DT_BUSINESS_DATA_ECHO_CONFIG
(
  ID                    VARCHAR2(30 char) not null
    constraint DT_BUSINESS_DATA_ECHO_CONFIG
      primary key,
  TEMPLATE_PRIMARY_KEY  VARCHAR2(30 char),
  OBJECT_ATTRIBUTE_NAME VARCHAR2(50 char),
  OBJECT_TYPE           VARCHAR2(2 char),
  QUERY_SQL             VARCHAR2(1000 char)
)
/

comment on table DT_BUSINESS_DATA_ECHO_CONFIG is '业务数据回显填充（配置表）'
/

comment on column DT_BUSINESS_DATA_ECHO_CONFIG.ID is '主键'
/

comment on column DT_BUSINESS_DATA_ECHO_CONFIG.TEMPLATE_PRIMARY_KEY is '模板主键'
/

comment on column DT_BUSINESS_DATA_ECHO_CONFIG.OBJECT_ATTRIBUTE_NAME is '对象属性名'
/

comment on column DT_BUSINESS_DATA_ECHO_CONFIG.OBJECT_TYPE is '对象类型（list<map<String,string>> 0\map<String,string> 1\string 2）'
/

comment on column DT_BUSINESS_DATA_ECHO_CONFIG.QUERY_SQL is '查询sql（返回属性名需要对应模板属性名）'
/


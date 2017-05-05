#GreenDao 3.0 学习
##简介
###GreenDao 是什么
[GreenDao官网链接](http://greenrobot.org/greendao/) 

####Entity中@Unique与@Index(unique = true)
Entity类中存在@Unique与@Index 时的pojo类与SQL

`public class PushMsg {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String title;
    @Index(unique = true)
    private String description;
    private Boolean isRead;
    private Integer type;
    private Date createTime;
}`

`CREATE TABLE android_metadata (locale TEXT);`

`CREATE TABLE "PUSH_MSG" ("_id" INTEGER PRIMARY KEY AUTOINCREMENT ,"TITLE" TEXT UNIQUE ,"DESCRIPTION" TEXT,"IS_READ" INTEGER,"TYPE" INTEGER,"CREATE_TIME" INTEGER);
`
`CREATE UNIQUE INDEX IDX_PUSH_MSG_DESCRIPTION ON "PUSH_MSG" ("DESCRIPTION" ASC);`

Entity类中无@Unique与@Index 时的pojo类与SQL

`public class PushMsg {
    @Id(autoincrement = true)
    private Long id;
//    @Unique
    private String title;
//    @Index(unique = true)
    private String description;
    private Boolean isRead;
    private Integer type;
    private Date createTime;
}`

`CREATE TABLE android_metadata (locale TEXT);`

`CREATE TABLE "PUSH_MSG" ("_id" INTEGER PRIMARY KEY AUTOINCREMENT ,"TITLE" TEXT,"DESCRIPTION" TEXT,"IS_READ" INTEGER,"TYPE" INTEGER,"CREATE_TIME" INTEGER);
`


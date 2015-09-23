# sunw-simple-search
根据class自动生成SQL查询条件语句，很实用

# 这是什么
sunw-simple-search是一款根据class属性的注解配置来动态生成查询语句的where部分。<br>
为什么说是simple呢？这是因为它较好的了支持单表and条件连接；<br>
但并不是说它不支持多表或者or连接，只是没能像单表and那么自动化。

# 为什么用
每个项目都会用到条件搜索查询，我们总重复着“获取条件->判断条件是否非空->拼接where条件”这样一系列简单而又繁琐的工作，如果条件很多，总是很让我们头疼，因为光是拼接条件的代码有可能就占用了我们整个方法的一半，有时候为了方便还不得不使用“1=1”这样永真耗时的条件，而使用sunw-simple-search只需要两三句简单代码就能帮你完整这些琐碎的工作，让我们远离那些没用的永真条件，提高我们的开发效率，让我们专注于更重要的业务逻辑。

# 它的优点
老实说，我写这个之前并没有接触过其他类似功能的工具，所以没办法把它与其他工具相比较，但就个人使用情况而言，它优点如下：
* 简单易用，基于注解配置，便于开发
* 支持order by语句的生成
* 支持一个字段的多个条件匹配
* 支持SQL分组（这里的分组不是只group by，二是指一个类中可以指定多个配置组，每次生成SQL时指定其中需要的一组或者多组字段）
* 支持多表条件查询和or条件查询（尽管不够智能不够自动化，但还是姑且算上吧）
* 支持表字段映射，能解决类属性与数据库字段不一致的情况
* 支持按参数生成SQL语句（对于没有传递参数值的属性字段，不会参与SQL生成）
* 支持HQL和SQL两种条件生成方式

# 注解说明
所有注解字段，当且仅当传递了对应参数且参数值不为空时（不需要参数值的匹配方式除外，例如NN(is not null)），该字段才会参与生成查询语句

# 条件匹配

* @Search
单条件匹配，可以通过注解属性配置匹配方式（type）、匹配分组（group，默认分组DefaultSearchGroup.class）
```
//表示username以like方式模糊匹配，所属分组有DefaultSearchGroup和FirstSearchGroup
@Search(type = SearchType.LK, group = {DefaultSearchGroup.class, FirstSearchGroup.class})
private String username;
```

* @Searches(searches = {})
多条件匹配，可以通过使一个字段进行多条件匹配，searches属性值为@Search数组<br>
当进行区间匹配时，需要对区间两个极端值进行区分，这个时候就用到了suffix属性，suffix表示参数名后缀，例如：
```
//表示查询年龄在某个区间的数据，此时传递的age最大值参数名必须为 age_le, 最小值参数名必须为 age_ge
@Searches(searches = {@Search(type = SearchType.GE, suffix = "ge"), @Search(type = SearchType.LE, suffix = "le")})
private int age;
```

# 排序
* @Order
排序语句，可以通过属性配置排序方式（type），排序优先级（sort，值越小，优先级越高）
```
//此配置最后生成语句为: order by age asc, id desc
@Order(type = OrderType.ASC, sort = 1)
private int age;
@Order(type = OrderType.DESC, sort = 2)
private long id;
```

* SearchUtil和SearchContext类
SearchContext为查询环境，封装了查询语句和查询参数，SearchUtil为工具类，用来生成查询环境
```
//object为参数对象，可以是任意Object类型或者Map类型，程序自动获取其类型，根据pojo的get方法取值，将object转成Map
//生成SQL查询语句
SearchContext sqlContext = SearchUtil.getSqlSearchContext(object);
//获取sql语句
String sql = sqlContext.getQueryString();
//获取此sql的参数
Map<String, Object> sqlParam = sqlContext.getParam();
//生成HQL查询语句
SearchContext hqlContext = SearchUtil.getSqlSearchContext(object);
//获取hql语句
String hql = hqlContext.getQueryString();
//获取hql查询参数
Map<String, Object> hqlParam = hqlContext.getParam();
```

# 方法细用
* SearchUtil.toParamMap(Object object)
该方法将任意类型实例转成Map对象，如果object == null则返回空HashMap<String, Object>;
* addSearchQuery(String query, String paramName, Object paramValue)
SearchContext实例中提供此方法，用于动态添加一个and条件匹配
* addOrderString(String query)
SearchContext实例中提供此方法，用于动态添加一个排序条件，目前仅支持低优先级排序条件追加，不支持高优先级排序条件添加


# Copyright
Copyright 2015 Sun Wang under [GNU GENERAL PUBLIC LICENSE Version 2, June 1991](LISENSE).





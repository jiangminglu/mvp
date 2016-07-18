# mvp
使用mvp深度解耦

### MVP的架构图

   ![mvp](/Users/jiangminglu/Desktop/mvp.jpg)

如图所示 我们可以看到 `View` 和 `Model` 没有任何联系，在我们传统的 `mvc`设计模式中，`view` 经常会去操作`Model`.

而在`MVP`里，`Presenter`完全把`Model`和`View`进行了分离，主要的程序逻辑在`Presenter`里实现。而且，`Presenter`与具体的`View`是没有直接关联的，而是通过定义好的接口进行交互，从而使得在变更`View`时候可以保持`Presenter`的不变，即重用！ 不仅如此，我们还可以编写测试用的`View`，模拟用户的各种操作，从而实现对`Presenter`的测试--而不需要使用自动化的测试工具。 我们甚至可以在`Model`和`View`都没有完成时候，就可以通过编写`Mock Object`（即实现了`Model`和`View`的接口，但没有具体的内容的）来测试`Presenter`的逻辑。

### 在本项目中MVP中所使用的类图如下:

  ![diagram](/Users/jiangminglu/Desktop/diagram.png)

整个类图分为3块区域

. 1 左边 `IBaseView`,`IWeatherView`,`WeatherActivity` 划分为`View` 层。

​     `IBaseView` 是为了定义一些公用的操作方法，比如 `showLoading`这样的方法

​    `IWeatherView` 实现 `IBaseView` 并定义了天气界面要操作的方法。

   `WeatherActivity`实现了`IWeatherView` 

. 2 中间 `BasePresenter`,`WeatherPresenter`,`DataCallback` 属于 `Presenter` 层

​    `BasePresenter`是所有 presenter 的父类。

​    `WeatherPresenter`定义并实现了对应View 要具体操作的事项。

​    `DataCallback`是一个泛型接口，定义要获取的数据类型，在presenter 和 Modle 两层之间解耦。

. 3 最右边 `BaseModel`,`WeatherModle`,`ResultCallback` 属于 `model` 层

​      `BaseModel`是所有 Model 的父类。

​    `WeatehrModel`主要是封装view传递过来的参数并向业务服务器发送request，当然还要负责返回数据的解析。

​    `HttpHelper` 主要是封装了网络请求。

​    `ResultCallback`是在modle 层和 http层进行解耦。



在整个设计中，用了两个 interface 来解耦。`ResultCallback`让`httpHelper` 独立出来，让它完全作为一个工具类，只负责发送请求，我们随时可以替换这一层的实现而不会有别的影响。`Datacallback` 让`Model` 变成正在的数据持久层，入参的封装，返回数据的封装，都可以放在这一层，在`Presenter`直接拿到界面想要的 javabean。

#### 具体的请求流程如下

 ![时序图](/Users/jiangminglu/Desktop/时序图.png)




该包中所有例子均使用zk客户端开源框架curator所开发

curator是apache针对zk原生api过于底层造成使用不方便的封装项目, 主要解决zk原生api的问题有:
    重试机制:提供可插拔的重试机制, 它将给捕获所有可恢复的异常配置一个重试策略, 并且内部也提供了几种标准的重试策略(比如指数补偿).
    连接状态监控: Curator初始化之后会一直的对zk连接进行监听, 一旦发现连接状态发生变化, 将作出相应的处理.
    zk客户端实例管理:Curator对zk客户端到server集群连接进行管理. 并在需要的情况, 重建zk实例, 保证与zk集群的可靠连接
    各种使用场景支持:Curator实现zk支持的大部分使用场景支持(甚至包括zk自身不支持的场景), 这些实现都遵循了zk的最佳实践, 并考虑了各种极端情况.

本文档也对例子中使用到的api进行总结:
    1. Curator几个组成部分:
        Client: 是ZooKeeper客户端的一个替代品, 提供了一些底层处理和相关的工具方法.
        Framework: 用来简化ZooKeeper高级功能的使用, 并增加了一些新的功能, 比如管理到ZooKeeper集群的连接, 重试处理
        Recipes: 实现了通用ZooKeeper的recipe, 该组件建立在Framework的基础之上
        Utilities:各种ZooKeeper的工具类
        Errors: 异常处理, 连接, 恢复等.
        Extensions: recipe扩展
    2. Client

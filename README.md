# 本项目的意义
对于nestedScrolling机制和behavior的讲解,这位大神(shehuan)讲的很好,推荐其博文(https://www.jianshu.com/p/b987fad8fcb4和https://www.jianshu.com/p/7830b05b38bb),仓库地址:https://github.com/SheHuan/BehaviorDemo.git;

然而其项目中TEST-3界面的头部触摸滚动的实现却非正确方式,属于拼凑的;请读者将其与我的项目的SlideDownShowTitle界面的头部触摸滚动实现方式做对比,我的是借鉴AppBarLayout.Behavior(继承自HeaderBehavior,头部触摸滚动的逻辑全在于此类)。


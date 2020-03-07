# 本项目的意义

1.如果想看Material Design的CoordinatorLayout结合自带的behaviors实现各种主流炫酷效果,请看https://github.com/saulmm/CoordinatorExamples.git,此项目据称作为代表作品之一参与GDG Summit 2015:'Tools and tps of
the trade for the Android developer'

2.关于nestedScrolling机制和behavior的讲解,国内这两位大神讲的最好
shehuan,推荐其博文https://www.jianshu.com/p/b987fad8fcb4和https://www.jianshu.com/p/7830b05b38bb,仓库地址:https://github.com/SheHuan/BehaviorDemo.git
gdutxiaoxu,推荐其系列博文https://blog.csdn.net/gdutxiaoxu/article/details/71732642,仓库地址:https://github.com/gdutxiaoxu/CoordinatorLayoutExample.git

3.本项目的意义除了帮助读者高效掌握自定义behavior,还在于更正前面两位大神实现头部触摸滑动的错误实现方式

请读者将shehuan的项目中TEST-3界面的头部触摸滚动的实现方式与本项目的SlideDownShowTitle界面的头部触摸滚动实现方式做对比,我的是借鉴AppBarLayout.Behavior(继承自HeaderBehavior,头部触摸滚动的逻辑全在于此类),是正统的实现方式。


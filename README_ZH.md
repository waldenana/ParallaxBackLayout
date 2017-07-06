# ParallaxBackLayout
[![Download](https://api.bintray.com/packages/anzewei/maven/com.github.anzewei/images/download.svg)](https://bintray.com/anzewei/maven/com.github.anzewei/_latestVersion)

仿微信的滑动返回Activity（视差滑动）.

[![Watch the video](https://github.com/anzewei/ParallaxBackLayout/blob/master/ext/video.png)](https://youtu.be/6da7UZh8MRk)

# Demo Apk

<a href="https://github.com/anzewei/ParallaxBackLayout/blob/master/ext/demo.apk?raw=true">DOWNLOAD</a>

# 使用说明

## 首先添加引用到Model的build.gradle

``` groovy
compile 'com.github.anzewei:parallaxbacklayout:lastversion'
``` 
	
## 在application中注册ParallaxHelper

``` java
     registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());
```
## 对需要滑动的activity增加annotation

``` java
@ParallaxBack
public class DetailActivity extends AppCompatActivity {
 。。。
}
```
- 这样DetailActivity就可以滑动返回了

#高级用法
##annotation高级配置

``` java
@ParallaxBack(edge = ParallaxBack.Edge.RIGHT,layout = ParallaxBack.Layout.PARALLAX)
````

注意：**其中edge控制滑动方向，layout设置滑动效果**

##滑动效果默认有3种方式

- PARALLAX 视差返回，类似微信的滑动返回效果
- COVER 上级activity不会滑动，只滑动当前activity，有点像抽屉
- SLIDE 跟随滑动，上一级会紧贴着当前activity滑动

##自定义滑动效果
- 首先实现滑动效果
``` java
public class MyTransform implements ITransform {
    @Override
    public void transform(Canvas canvas, ParallaxBackLayout parallaxBackLayout, View child) {
        //这里对canvas做变换就可以了，可以看一下 CoverTransform ParallaxTransform SlideTransform
    }
}

```

- 设置自定义效果

``` java
ParallaxBackLayout layout = ParallaxHelper.getParallaxBackLayout(activity, true);
layout.setLayoutType(LAYOUT_CUSTOM,new MyTransform());
```

##全屏滑动和边缘滑动

``` java
ParallaxBackLayout layout = ParallaxHelper.getParallaxBackLayout(activity, true);
layout.setEdgeMode(EDGE_MODE_FULL);//全屏滑动
layout.setEdgeMode(EDGE_MODE_DEFAULT);//边缘滑动
```

##禁用返回

如果需要对DetailActivity进行滑动返回的控制，如某些情况不希望滑动，那可以使用以下代码


``` java
@ParallaxBack
public class DetailActivity extends AppCompatActivity {
     private void disableBack(){
         ParallaxHelper.getInstance().disableParallaxBack(this);
     }
}
```
##更多api
- setShadowDrawable  
   设置阴影
- setEdgeFlag  
  设置滑动方向
- setLayoutType  
   设置切换方式
- setSlideCallback  
  设置滑动回调
- setScrollThresHold   
  滑动比例超过设置值以后会finish当前activity
- setBackgroundView   
  设置上级view的绘制方式，可继承自GoBackView，在draw方法中做特殊处理

# 版本建议
 如果不能获取到application，或者希望兼容4.0以下的版本，建议使用

``` groovy
compile 'com.github.anzewei:parallaxbacklayout:0.5'
```

# 更新
- 日期 2017.06.19  版本  1.1.6
更新了annotation
- 日期 2017.06.19  版本  1.1.2
提供更多接口
新增自定义切换样式
新增自定义阴影
- 日期 2017.06.17  版本  1.1.1
新增多种滑动方向，上下左右都支持；
新增切换样式，视差 、覆盖 、无视差
- 日期 2017.05.16  版本  1.0
使用更简单的方法实现，不需要继承基类

# License

Copyright 2017 anzewei

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

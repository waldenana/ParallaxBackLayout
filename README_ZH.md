# ParallaxBackLayout
[![Download](https://api.bintray.com/packages/anzewei/maven/com.github.anzewei/images/download.svg)](https://bintray.com/anzewei/maven/com.github.anzewei/_latestVersion)
仿微信的滑动返回Activity（视差滑动）.

<img width="480" height="847" src="https://github.com/anzewei/ParallaxBackLayout/blob/master/ext/v0.2.gif" />

# Demo Apk

<a href="https://github.com/anzewei/ParallaxBackLayout/blob/master/ext/demo.apk?raw=true">DOWNLOAD</a>

# 使用说明

## 首先添加引用到Model的build.gradle

``` groovy
compile 'com.github.anzewei:parallaxbacklayout:1.0'
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
如果需要对DetailActivity进行滑动返回的控制，如某些情况不希望滑动，那可以使用以下代码


``` java
@ParallaxBack
public class DetailActivity extends AppCompatActivity {
     private void disableBack(){
         ParallaxHelper.getInstance().getParallaxBackLayout(this).setEnableGesture(false);
     }
}
```

# 版本建议
 如果不能获取到application，或者希望兼容4.0以下的版本，建议使用

``` groovy
compile 'com.github.anzewei:parallaxbacklayout:0.5'
```

# 更新
- 日期 2017.05.16  版本  1.0
使用更简单的方法实现，不需要继承基类

# License

Copyright 2017 anzewei

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

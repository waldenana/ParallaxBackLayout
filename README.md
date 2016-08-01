# ParallaxBackLayout

Parallax finish Activity.

<img width="480" height="847" src="https://github.com/anzewei/ParallaxBackLayout/blob/master/ext/v0.2.gif" />

# Demo Apk

<a href="https://github.com/anzewei/ParallaxBackLayout/blob/master/ext/demo.apk?raw=true">DOWNLOAD</a>


<a href="https://github.com/anzewei/ParallaxBackLayout/blob/master/README_ZH.md">简体中文</a>

# Usage

## Step 1

- Add these lines to your build.gradle

``` groovy
compile 'com.github.anzewei:parallaxbacklayout:0.4'
``` 
	
## Step 2

- Make your Activitys extend `ParallaxActivityBase`

``` java
public class DetailActivity extends ParallaxActivityBase {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBackEnable(true);//enable Parallax back
		setContentView(R.layout.content_main);
	}

}
```
- Add these lines to your AppTheme 

```xml
    <item name="android:windowAnimationStyle">@style/Animation_Right</item>
```

# Other Usage

- Your Activitys can extends Activity or FragmentActivity es.

``` java
 public class MainActivity extends Activity/FragmentActivity/AppCompatActivity... {
    private ParallaxBackActivityHelper mHelper;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHelper.onActivityDestroy();
    }

    public ParallaxBackLayout getBackLayout() {
        return mHelper.getBackLayout();
    }

    public void setBackEnable(boolean enable) {
        getBackLayout().setEnableGesture(enable);
    }

    public void scrollToFinishActivity() {
        mHelper.scrollToFinishActivity();
    }

    @Override
    public void onBackPressed() {
        scrollToFinishActivity();
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mHelper = new ParallaxBackActivityHelper(this);
    }
}
```

# License

Copyright 2015 anzewei

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

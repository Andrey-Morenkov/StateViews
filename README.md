# State Views for Android (BETA version)
Create & Show progress, data or error views, the easy way!

<img src="/art/StatusPage.jpg" width="80%" alt="Android States Page Cover"/>


StateViews is based on ViewSwitcher mechanism and allows to handle the different app states, from loading... to displaying
data and error views, the library is tiny and fully customizable.

This repo has been developed from https://github.com/medyo/StateViews.

Different features of original repo:
- Codes converted to Kotlin
- Fixed some memory leaks
- Icons colors can be customized separately
- Added showing state status

```xml
<com.umutbey.stateviews.StateView
        android:id="@+id/status_page"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:text="Hello World!" />

 </com.umutbey.stateviews.StateView>
```
Then Call the appropriate state:

```java
mStatusPage.displayLoadingState();
```

```java
mStatusPage.displayState("TAG_NAME");
```

```java
mStatusPage.hideStates();
```



## Setup
[![](https://jitpack.io/v/kobeumut/StateViews.svg)](https://jitpack.io/#kobeumut/StateViews)

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```
implementation 'com.github.kobeumut:StateViews:0.5'
```

## Usage

### 1. Available attributes for PageStatus Builder

| Function        | Description  |
| ------------- |:-------------:|
| setState(params) | Create a new state. Params are tag, title, description, icon, spesific icon color, button text, clicklistener|
| setIconColor(Int) | Set Icon color for all icons(Same color) |
| setIconSize(Int) | Set Icon Size |
| setTextColor(Int) |  set Title and description colors|
| setFontFace(String) | Set Custom font |
| setButtonBackgroundColor(Int) | Set Button Background color|
| setButtonTextColor(String) | Set Button Text color|

### 2. Available attributes for PageStatesView


| Function        | Description  |
| ------------- |:-------------:|
| displayState(String) | Display a state by his tag name. If you want to send displayState("hide") parameter, state will hide |
| hideStates() | Hide all states and display data|
| displayLoadingState() | Display the loading state|
| addCustomState(Intent) | Create a new state only available for the current activity, fragment...|
| setOnStateButtonClicked(View.OnClickListener) |  Click listener for the state button|
| applyGravity(Int) | Set View Gravity |

### 3. Samples
#### Display an Error View

![No Connection](/art/no_connection.jpg)

```java
setState(
    "TAG_ERROR",
    "No Connection",
    "Error retrieving information from server.",
    AppCompatResources.getDrawable(this, R.drawable.ic_server_error),
    Color.Black,
    "Retry"
    );

mStatusPage.displayState("TAG_ERROR");
```

#### Display a "no Data" View

![No Data](/art/no_data.jpg)

```java
setState(
    "TAG_NO_RESULTS",
    "No Results Found",
    "Unfortunately I could not find any results matching your search",
    AppCompatResources.getDrawable(this, R.drawable.search), ,null, null
)

mStatusPage.displayState("TAG_NO_RESULTS");
```

## Contribute
Contributions are welcome!

## ProGuard
Nothing to include

## License

~~~
Copyright (c) 2018 Mehdi Sakout
Copyright (c) 2018 Mustafa Umut ADALI.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
~~~

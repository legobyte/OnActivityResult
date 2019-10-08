# OnActivityResult
Start activityForResult from anywhere withing your application, all you need is a `Context`

## Usage
````kotlin
Proxy.with(context = applicationContext).listener { requestCode, resultCode, data ->
    // do something with your result
    if(resultCode == Proxy.RESULT_INTENT_UNHANDLED){
        // in this case the intent that you have provided does not match to an activity in this device.
    }
}.launch(Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }, requestCode = MY_REQUEST_CODE)
````
`requestCode` is optional

## Setup
In your ProjectLevel `build.gradle`
````gradle
allprojects {
    repositories {
        jcenter()
    }
}
````
In your AppLevel `build.gradle`
````gradle
dependencies {
    implementation 'org.legobyte:onactivityresult:1.0.0'
}
````

## License

    Copyright 2019 SunriseCo

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

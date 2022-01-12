# HelloCustomer SDK

HelloCustomer touchpoint SDK for Android platform.

### Requirements

Minimum Android SDK 21

### Installation

You will need to add the jitpack.io repository:
```Groovy
allprojects {
 repositories {
    jcenter()
    maven { url "https://jitpack.io" }
 }
}
```
and:
```Groovy

```

### Usage

To load a survey you have to call `com.hellocustomer.sdk.loadTouchpoint` extension function within 
`androidx.appcompat.app.AppCompatActivity` or `androidx.fragment.app.Fragment`. If you want to invoke
this function in other class, you have to provide an context as argument, then FragmentManager to show
the dialog on success.

```Kotlin
import com.hellocustomer.sdk.HelloCustomerTouchpointConfig
import com.hellocustomer.sdk.font.FontBuilder
import com.hellocustomer.sdk.HelloCustomerSdk

// imports...

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        HelloCustomerSdk.loadTouchpoint(
            context = this, // for this example the context is provided in the Activity but it isn't necessary
            config = HelloCustomerTouchpointConfig(
                authorizationToken = "<YOUR AUTHORIZATION HEADER HERE>",
                companyId = "<YOUR COMPANY_ID HERE>",
                touchpointId = "<YOUR TOUCHPOINT_ID HERE>",
                metadata = mapOf(
                    "appVersion" to BuildConfig.VERSION_CODE.toString() // custom metadata
                ),
                respondentFirstName = "John", //optional user's first name
                respondentLastName = "Smith", //optional user's last name
                respondentEmailAddress = "john@smith.com", //optional user's email address
                questionFont = FontBuilder.FromId(R.font.bad_script), //optional, default android typeface will be used
                hintFont = FontBuilder.FromId(R.font.architects_daughter) //optional, default android typeface will be used
            ), 
            onSuccess = { helloCustomerDialog: HelloCustomerDialog ->
                helloCustomerDialog.show(supportFragmentManager)
            },
            onError = { throwable: Throwable ->
                Log.e("ERROR", "An error occurred.", throwable)
            }
        )
    }
}
```

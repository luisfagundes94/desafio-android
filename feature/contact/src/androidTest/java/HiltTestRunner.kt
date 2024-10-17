import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication
import kotlin.jvm.java

class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: java.lang.ClassLoader?,
        className: String?,
        context: android.content.Context?
    ): android.app.Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}

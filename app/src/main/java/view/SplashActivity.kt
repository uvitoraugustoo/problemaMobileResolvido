package view


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.kleberson.problemamobile.R

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_TIME: Long = 2500 // tempo em ms
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish() // fecha a Splash para não voltar com o botão voltar
        }, SPLASH_TIME)
    }
}

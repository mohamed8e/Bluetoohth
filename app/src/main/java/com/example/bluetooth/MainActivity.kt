package com.example.bluetooth

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_BT:Int = 1
    private val REQUEST_CODE_ENABLE_BT:Int = 2
    lateinit var turnOnButn : Button
    lateinit var bluetootIv : ImageView
    lateinit var bluetoohStatutsTv:TextView
    lateinit var paireBtn : Button
    lateinit var discoverbeBtn : Button
    lateinit var pairedTv : TextView
    lateinit var turnOfButn : Button
    lateinit var bAdapter : BluetoothAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bluetoohStatutsTv = findViewById(R.id.bluetoohStatutsTv)
        bluetootIv = findViewById(R.id.bluetootIv)
        turnOnButn = findViewById(R.id.turnOnButn)
        turnOfButn = findViewById(R.id.turnOfButn)
        pairedTv = findViewById(R.id.pairedTv)
        paireBtn = findViewById(R.id.paireBtn)
        discoverbeBtn = findViewById(R.id.discoverbeBtn)
        bAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bAdapter==null){
            bluetoohStatutsTv.text = "Bluetooth is not avaibele"
        }else{
            bluetoohStatutsTv.text = " Bluetooth is avaibele"
        }
        if (bAdapter.isEnabled){
            bluetootIv.setImageResource{R.drawable.ic_bluethid_on}
        }
        else{
            bluetootIv.setImageResource { R.drawable.ic_bluethid_off }
        }
        turnOnButn.setOnClickListener {
            if (bAdapter.isEnabled){
                Toast.makeText(this,"Already on",Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return@setOnClickListener
                }
                startActivityForResult(intent,REQUEST_CODE_BT)
            }
        }
        turnOfButn.setOnClickListener {
            if (!bAdapter.isEnabled){
                Toast.makeText(this,"Already off",Toast.LENGTH_SHORT).show()
            }
            else{
               bAdapter.disable()
                bluetootIv.setImageResource { R.drawable.ic_bluethid_off }
                Toast.makeText(this,"Bluetooth turned off",Toast.LENGTH_SHORT).show()
            }

        }
        discoverbeBtn.setOnClickListener {
            if (!bAdapter.isDiscovering){
                Toast.makeText(this,"Making Your device discoverabel",Toast.LENGTH_SHORT).show()
                val intent = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivity(intent,REQUEST_CODE_ENABLE_BT)
            }
        }
        paireBtn.setOnClickListener {
            if (bAdapter.isEnabled){
                pairedTv.text = "Paired Devices"
                val devices = bAdapter.bondedDevices
                for (device in devices){
                    val devicName = device.name
                    val deviceAddress = device
                    pairedTv.append("\n Device : $devicName , $device")
                    pairedTv.append("\n")
                }
            }

            else{
                Toast.makeText(this,"Turn on bluetooh first",Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun startActivity(intent: Intent, requestCodeBt: Int) {
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            REQUEST_CODE_BT ->
                if (resultCode == Activity.RESULT_OK){
                    bluetootIv.setImageResource(R.drawable.ic_bluethid_on)
                        Toast.makeText(this,"Bluetooth is on ",Toast.LENGTH_SHORT).show()
                    }else{
                    Toast.makeText(this,"Could not on bluetoohth",Toast.LENGTH_SHORT).show()

                }
        }


        super.onActivityResult(requestCode, resultCode, data)
    }
}





private fun ImageView.setImageResource(function: () -> Int) {

}

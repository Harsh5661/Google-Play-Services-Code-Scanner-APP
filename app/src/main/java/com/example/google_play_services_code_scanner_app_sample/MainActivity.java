package com.example.google_play_services_code_scanner_app_sample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanButton = findViewById(R.id.scanButton);
        EditText resultEditText = findViewById(R.id.resultEditText);

        scanButton.setOnClickListener(view -> {
            GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
                    .setBarcodeFormats(
                            Barcode.FORMAT_ALL_FORMATS)
                    .build();

            GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(this);
            scanner.startScan()
                    .addOnSuccessListener(
                            barcode -> {
                                String scannedResult = barcode.getRawValue();
                                resultEditText.setText(scannedResult);
                                Toast.makeText(getApplicationContext(),
                                        "SUCCESS: " + scannedResult, Toast.LENGTH_LONG).show();
                            })
                    .addOnFailureListener(
                            e -> {
                                Log.d("CODE_SCAN_FAILED", e.getMessage());
                                Toast.makeText(getApplicationContext(),
                                        "Failed to scan barcode", Toast.LENGTH_SHORT).show();
                            });
        });

        // Adding click listener to resultEditText for copying text
        resultEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyTextToClipboard(resultEditText.getText().toString());
                Toast.makeText(getApplicationContext(),
                        "Text copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void copyTextToClipboard(String text) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }
    }
}

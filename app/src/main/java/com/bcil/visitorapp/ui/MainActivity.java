package com.bcil.visitorapp.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.room.Room;

import com.bcil.visitorapp.R;
import com.bcil.visitorapp.database.DatabaseHandler;
import com.bcil.visitorapp.model.VisitorData;
import com.bcil.visitorapp.utils.AppConstants;
import com.bcil.visitorapp.utils.CommonUtils;
import com.bcil.visitorapp.utils.Validation;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.media.MediaRecorder.VideoSource.CAMERA;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_WRITE_FILE = 111;
    private static final int ACTIVITY_START_CAMERA_APP = 200;
    private static final int REQUEST_CAMERA = 190;
    @Bind(R.id.etCName)
    MaterialEditText etCName;
    @Bind(R.id.etName)
    MaterialEditText etName;
    @Bind(R.id.etDesgn)
    MaterialEditText etDesgn;
    @Bind(R.id.etLoc)
    MaterialEditText etLoc;
    @Bind(R.id.etEmail)
    MaterialEditText etEmail;
    @Bind(R.id.etMobile)
    MaterialEditText etMobile;
    @Bind(R.id.etReq)
    MaterialEditText etReq;
    @Bind(R.id.etConnect)
    MaterialEditText etConnect;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private DatabaseHandler databaseHandler;
    private XSSFSheet sheet;
    private XSSFWorkbook workbook;
    private File fileDirectory;
    private Uri path;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private String selectFilePath;
    private File xlsxfilepath;
    private String xlsxFileLocation;
    private Uri xlsxURI;
    private FileOutputStream outputStream;
    private PackageManager pm;
    private String CAPTURE_TIME_IMAGE_ONE;
    private String pictureImagePathOne;
    private File image;
    private Uri photoURI;
    private Bitmap bmThumbnail;
    private File destination;
    private String filename;
    private File photoFile;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            verifyStoragePermissions(this);
            verifyCameraPermission(this);
        } else {
            filePermission();
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        pm = getPackageManager();
        initDb();
        initAlertDialog();
        initView();

    }

    private void initView() {
        etCName.requestFocus();
        etDesgn.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(i==0){
                    new CommonUtils().Alert(MainActivity.this,"Alert","Please capture photo first");
                }
            }
        });
        etLoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(i==0){
                    new CommonUtils().Alert(MainActivity.this,"Alert","Please capture photo first");
                }
            }
        });
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(i==0){
                    new CommonUtils().Alert(MainActivity.this,"Alert","Please capture photo first");
                }
            }
        });
        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(i==0){
                    new CommonUtils().Alert(MainActivity.this,"Alert","Please capture photo first");
                }
            }
        });
        etReq.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(i==0){
                    new CommonUtils().Alert(MainActivity.this,"Alert","Please capture photo first");
                }
            }
        });
        etConnect.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(i==0){
                    new CommonUtils().Alert(MainActivity.this,"Alert","Please capture photo first");
                }
            }
        });
    }

    private void verifyCameraPermission(MainActivity mainActivity) {
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_CAMERA = new String[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PERMISSIONS_CAMERA = new String[]{
                    Manifest.permission.CAMERA
            };
        }
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    mainActivity,
                    PERMISSIONS_CAMERA,
                    CAMERA
            );
        }
    }

    private void initAlertDialog() {
        CustomDialogClass cdd = new CustomDialogClass(MainActivity.this);
        cdd.setCancelable(false);
        cdd.show();

    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void filePermission() {
        boolean result = checkpermission();
        if (result) {
            createFile();
        }
    }

    private void createFile() {
        new File(AppConstants.SERVER_FILE_PATH);
        copySettings("Service.txt");
    }

    private void copySettings(String settingsFile) {
        try {
            if (!new CommonUtils().checkSettingFile(AppConstants.SERVER_FILE_PATH, settingsFile)) {
                new CommonUtils().copyAssets(MainActivity.this, settingsFile, AppConstants.SERVER_FILE_PATH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkpermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Write file permission is necessary to read info!!!");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_FILE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_FILE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private void initDb() {
        databaseHandler = Room.databaseBuilder(this,
                DatabaseHandler.class, "visitor-db")
                .build();

    }


    @OnClick({R.id.btSave, R.id.btDownload, R.id.btClear, R.id.btCapture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btSave:
                if (checkValidation()) {
                    if(i==0){
                        new CommonUtils().Alert(MainActivity.this,"Alert","Please capture photo first");
                    }else {
                        new AsyncInsertData(etCName.getText().toString().trim(), etName.getText().toString().trim(), etDesgn.getText().toString().trim(), etLoc.getText().toString().trim(), etEmail.getText().toString().trim(), etMobile.getText().toString().trim(), etReq.getText().toString().trim(), etConnect.getText().toString().trim()).execute();

                    }
                } else {
                    Toast.makeText(this, "Please enter the required fields", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btDownload:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Alert");
                alertDialogBuilder
                        .setMessage("Are you sure want to view report.")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new AsyncWriteData().execute();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            case R.id.btClear:
                clearTextField();
                break;
            case R.id.btCapture:
                if(checkValidation1()){
                    capturePhoto();
                }else {
                    Toast.makeText(this, "Please enter the required fields", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    private void capturePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
              Intent callCameraApplicationIntent = new Intent();
                callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

                photoFile = createImageFile();

                photoURI = FileProvider.getUriForFile(MainActivity.this, getPackageName() + ".provider", photoFile);
                callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(MainActivity.this, getPackageName() + ".provider", photoFile));

                startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);

            } else {
                makeText(getBaseContext(), "Camera is not available", LENGTH_LONG).show();
            }


        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);


        }
    }

    private File createImageFile() {
        final String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_"+etCName.getText().toString().trim()+"_"+etName.getText().toString().trim()+"_"+timestamp+".jpg";
        File storageDir = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)));
        storageDir.mkdirs();
        return  new File(storageDir,imageFileName);
    }

    private void clearTextField() {
        etCName.setText(AppConstants.EMPTY);
        etName.setText(AppConstants.EMPTY);
        etDesgn.setText(AppConstants.EMPTY);
        etLoc.setText(AppConstants.EMPTY);
        etEmail.setText(AppConstants.EMPTY);
        etMobile.setText(AppConstants.EMPTY);
        etReq.setText(AppConstants.EMPTY);
        etConnect.setText(AppConstants.EMPTY);
        etCName.requestFocus();
        new CommonUtils().hideKeyboardOnLeaving(MainActivity.this);
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(etCName)) ret = false;
        if (!Validation.hasText(etName)) ret = false;
        if (!Validation.hasText(etDesgn)) ret = false;
        if (!Validation.hasText(etLoc)) ret = false;
        if (!Validation.checkMobileNumber(etMobile)) ret = false;
        if (!Validation.checkEmailAddress(etEmail)) ret = false;
        return ret;
    }
    private boolean checkValidation1() {
        boolean ret = true;
        if (!Validation.hasText(etCName)) ret = false;
        if (!Validation.hasText(etName)) ret = false;
        return ret;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_manu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_download:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Alert");
                alertDialogBuilder
                        .setMessage("Are you sure want to view report.")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new AsyncWriteData().execute();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncInsertData extends AsyncTask<Object, Object, String> {
        private String cname, name, desgn, loc, emailid, mobileno, soln, connect;

        AsyncInsertData(String cname, String name, String desgn, String loc, String emailid, String mobileno, String soln, String connect) {
            this.cname = cname;
            this.name = name;
            this.desgn = desgn;
            this.loc = loc;
            this.emailid = emailid;
            this.mobileno = mobileno;
            this.soln = soln;
            this.connect = connect;
        }

        @Override
        protected String doInBackground(Object... objects) {
            databaseHandler.daoAccess().insertOnlySingleRecord(new VisitorData(cname, name, desgn, loc, emailid, mobileno, soln, connect, AppConstants.NAME));
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity.this, "Data saved successfully.", Toast.LENGTH_SHORT).show();
            clearTextField();
            i = 0;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncWriteData extends AsyncTask<Object, Object, String> {

        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Please wait exporting data", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Object... objects) {
            List<VisitorData> visitorDataList = databaseHandler.daoAccess().fetchAllData();
            InputStream stream = getResources().openRawResource(R.raw.template);
            try {
                workbook = new XSSFWorkbook(stream);
                sheet = workbook.getSheetAt(0);
            } catch (IOException e) {
                e.printStackTrace();
            }

            XSSFCellStyle style = workbook.createCellStyle();
            style.setBorderTop((short) 6); // double lines border
            style.setBorderBottom((short) 1); // single line border
            XSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setFontName("Arial");
            font.setBold(true);
            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
            style.setFont(font);

            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("Company Name");
            cell.setCellStyle(style);


            Cell cell2 = row.createCell(1);
            cell2.setCellValue("Name");
            cell2.setCellStyle(style);

            Cell cell3 = row.createCell(2);
            cell3.setCellValue("Designation");
            cell3.setCellStyle(style);


            Cell cell4 = row.createCell(3);
            cell4.setCellValue("Location");
            cell4.setCellStyle(style);


            Cell cell5 = row.createCell(4);
            cell5.setCellValue("Official EmailId");
            cell5.setCellStyle(style);

            Cell cell6 = row.createCell(5);
            cell6.setCellValue("MobileNumber");
            cell6.setCellStyle(style);

            Cell cell7 = row.createCell(6);
            cell7.setCellValue("Solution Interested in or Requirement");
            cell7.setCellStyle(style);

            Cell cell8 = row.createCell(7);
            cell8.setCellValue("Good time to connect");
            cell8.setCellStyle(style);

            Cell cell9 = row.createCell(8);
            cell9.setCellValue("AttendedBy");
            cell9.setCellStyle(style);

            for (int i = 0; i < visitorDataList.size(); i++) {
                Row row8 = sheet.createRow(1 + i);
                Cell cell19 = row8.createCell(0);
                cell19.setCellValue(visitorDataList.get(i).getCompanyname());
                Cell cell20 = row8.createCell(1);
                cell20.setCellValue(visitorDataList.get(i).getName());
                Cell cell21 = row8.createCell(2);
                cell21.setCellValue(visitorDataList.get(i).getDesignation());
                Cell cell22 = row8.createCell(3);
                cell22.setCellValue(visitorDataList.get(i).getLoc());
                Cell cell23 = row8.createCell(4);
                cell23.setCellValue(visitorDataList.get(i).getEmailid());
                Cell cell24 = row8.createCell(5);
                cell24.setCellValue(visitorDataList.get(i).getMobileno());
                Cell cell25 = row8.createCell(6);
                cell25.setCellValue(visitorDataList.get(i).getSolutions());
                Cell cell26 = row8.createCell(7);
                cell26.setCellValue(visitorDataList.get(i).getTime());
                Cell cell27 = row8.createCell(8);
                cell27.setCellValue(visitorDataList.get(i).getAttendby());
            }

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    File file = createXlsxFile();
                    path = FileProvider.getUriForFile(MainActivity.this, getPackageName() + ".provider", file);
                    outputStream = new FileOutputStream(file.getAbsoluteFile());

                } else {
                    fileDirectory = new File(Environment.getExternalStorageDirectory() + "/BCIL/VisitorData/", "VisitorEnteredData.xlsx");
                    path = Uri.fromFile(fileDirectory);
                    outputStream = new FileOutputStream(fileDirectory.getAbsolutePath());

                }
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                Log.d(TAG, "CheckedException:" + e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "Data Export Done Successfully.", Toast.LENGTH_SHORT).show();
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(path, "application/vnd.ms-excel");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this,
                        "No application found to display spread sheet.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private File createXlsxFile() {
        final String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        final String imageFileName = "XLSX_" + "VisitorData_"+timestamp+".xlsx";
        File storageDir = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)));
        storageDir.mkdirs();
        return new File(storageDir,imageFileName);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_FILE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createFile();
            } else {
                Toast.makeText(this, "Please grant the permission to allocate file", Toast.LENGTH_SHORT).show();
            }
        } else if(requestCode==REQUEST_EXTERNAL_STORAGE){
            if (grantResults.length <= 0
                    || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(MainActivity.this, "Cannot write images to external storage", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
                    i = 1;
                    Toast.makeText(this, "Captured image successfully", Toast.LENGTH_SHORT).show();
                }

            } else {
                if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
                    onCaptureImageResult(data);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void onCaptureImageResult(Intent data) {
        if (data.getExtras() != null)
            bmThumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        if (bmThumbnail != null) {
            bmThumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        }
        final String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_"+etCName.getText().toString().trim()+"_"+etName.getText().toString().trim()+"_"+timestamp+".jpg";
        destination = new File(Environment.getExternalStorageDirectory(),
                imageFileName);
        filename = String.valueOf(destination);
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

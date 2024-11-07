package kiwu.android.piccheckstart.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;

import kiwu.android.piccheckstart.R;
import kiwu.android.piccheckstart.controller.ListCategoryController;
import kiwu.android.piccheckstart.controller.TaskFileController;
import kiwu.android.piccheckstart.model.TaskModel;

public class MainActivity extends AppCompatActivity {

    private TaskFileController controller;
    private ListFragment listFragment;
    private CalendarFragment calFragment;
    private StorageFragment strgFragment;
    private CreateFragment createFragment;
    private CategoryFragment categoryFragment;
    private CameraFragment cameraFragment;

    private BottomNavigationView btmNavigation;
    private ListCategoryController categoryController;
    private TabLayout tabLayout;
    private Toolbar topToolbar;

    private String[] category = {"hello", "this", "is", "me"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 처음 화면에 ListFragment 추가
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frmMain, new ListFragment())
                    .commit();
        }

        // Initialize views
        topToolbar = findViewById(R.id.topToolbar);
        setSupportActionBar(topToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        tabLayout = findViewById(R.id.categoryTab);  // TabLayout 초기화
        btmNavigation = findViewById(R.id.btmNavigation);  // BottomNavigationView 초기화

        // Initialize spinner
        category = getResources().getStringArray(R.array.category);
        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.category, R.layout.spinner_dropdown_item);
        Spinner navigationSpinner = new Spinner(getSupportActionBar().getThemedContext());
        navigationSpinner.setAdapter(spinnerAdapter);
        topToolbar.addView(navigationSpinner, 0);

        // Set spinner item selection listener
        navigationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle item selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        try {
            controller = new TaskFileController(getApplicationContext().getFilesDir().getPath());
        } catch(IOException ioe) {
            Toast.makeText(this, ioe.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // Initialize fragments
        listFragment = new ListFragment();
        calFragment = new CalendarFragment();
        strgFragment = new StorageFragment();
        createFragment = new CreateFragment();
        categoryFragment = new CategoryFragment();
        cameraFragment = new CameraFragment();

        // Set bottom navigation item selected listener
        if (btmNavigation != null) {
            btmNavigation.setOnNavigationItemSelectedListener(new BottomNavigationItemSelectedListener());
        }

        categoryController = new ListCategoryController();

        // Start with the LIST_FRAGMENT
        changeFragment(LIST_FRAGMENT);
    }

    public void launchCameraFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frmMain, new CameraFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectedItemId = item.getItemId();

        if (selectedItemId == R.id.top_navi_item_edit) {
            changeFragment(LIST_FRAGMENT);
        } else if (selectedItemId == R.id.top_navi_item_cate) {
            changeFragment(CATEGORY_FRAGMENT);
        }

        return super.onOptionsItemSelected(item);
    }

    // Inflate menus
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        getMenuInflater().inflate(R.menu.top_app_bar_category, menu);
        return true;
    }

    public class BottomNavigationItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int selectedItemId = item.getItemId();

            if (selectedItemId == R.id.btm_navi_item_list) {
                changeFragment(LIST_FRAGMENT);
            } else if (selectedItemId == R.id.btm_navi_item_calendar) {
                changeFragment(CALENDAR_FRAGMENT);
            } else if (selectedItemId == R.id.btm_navi_item_storage) {
                changeFragment(STORAGE_FRAGMENT);
            } else if (selectedItemId == R.id.btm_item_create_save || selectedItemId == R.id.btm_item_create_cancel) {
                changeFragment(LIST_FRAGMENT);
            }

            if (item.getItemId() == R.id.btm_item_create_save) {
                if (controller != null && createFragment != null) {
                    TaskModel taskModel = createFragment.getTaskModel();

                    try {
                        controller.create(taskModel);
                        Toast.makeText(MainActivity.this, "저장 완료", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }

            return true;
        }
    }

    public static final int LIST_FRAGMENT = 0;
    public static final int CALENDAR_FRAGMENT = 1;
    public static final int STORAGE_FRAGMENT = 2;
    public static final int CREATE_FRAGMENT = 3;
    public static final int CATEGORY_FRAGMENT = 4;
    public static final int CAMERA_FRAGMENT = 5;

    // Fragment switching logic
    public void changeFragment(int fragmentNum) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        switch(fragmentNum) {
            case LIST_FRAGMENT:
                ft.replace(R.id.frmMain, listFragment);
                if (btmNavigation != null) btmNavigation.setVisibility(View.VISIBLE);
                if (tabLayout != null) tabLayout.setVisibility(View.VISIBLE);
                if (topToolbar != null) topToolbar.setVisibility(View.VISIBLE);
                ft.commitNow();
                break;
            case CALENDAR_FRAGMENT:
                ft.replace(R.id.frmMain, calFragment);
                ft.commitNow();
                break;
            case STORAGE_FRAGMENT:
                ft.replace(R.id.frmMain, strgFragment);
                ft.commitNow();
                break;
            case CREATE_FRAGMENT:
                ft.replace(R.id.frmMain, createFragment);
                if (btmNavigation != null) btmNavigation.setVisibility(View.GONE);
                if (tabLayout != null) tabLayout.setVisibility(View.GONE);
                if (topToolbar != null) topToolbar.setVisibility(View.GONE);
                ft.commitNow();
                break;
            case CATEGORY_FRAGMENT:
                ft.replace(R.id.frmMain, categoryFragment);
                if (btmNavigation != null) btmNavigation.setVisibility(View.GONE);
                if (tabLayout != null) tabLayout.setVisibility(View.GONE);
                if (topToolbar != null) topToolbar.setVisibility(View.GONE);
                ft.commitNow();
                break;
            case CAMERA_FRAGMENT:
                ft.replace(R.id.frmMain, cameraFragment);
                if (btmNavigation != null) btmNavigation.setVisibility(View.GONE);
                if (tabLayout != null) tabLayout.setVisibility(View.GONE);
                ft.commitNow();
                break;
        }
    }

    public ArrayList<TaskModel> retrieveAll() {
        ArrayList<TaskModel> tasks = new ArrayList<>();

        try {
            tasks = controller.retrieveAll();
        } catch(IOException ioe) {
            Toast.makeText(this, ioe.getMessage(), Toast.LENGTH_SHORT).show();
        } // end try~catch

        return tasks;
    } // end retrieveAll

}

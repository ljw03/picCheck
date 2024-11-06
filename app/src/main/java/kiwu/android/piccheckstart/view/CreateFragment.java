package kiwu.android.piccheckstart.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kiwu.android.piccheckstart.R;
import kiwu.android.piccheckstart.controller.TaskFileController;
import kiwu.android.piccheckstart.model.TaskModel;

import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public CreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateFragment newInstance(String param1, String param2) {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private EditText etTitle;
    private EditText etDate;
    private EditText etTime;
    private EditText etCategory;

    public TaskModel getTaskModel() {
        String title = etTitle.getText().toString();
        String date = etDate.getText().toString();
        String time = etTime.getText().toString();
        String category = etCategory.getText().toString();
        return new TaskModel(title, date, time, category); // 다른 필드는 기본값으로 설정하거나 필요 시 추가
    }

    private TaskFileController controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_create, container, false);

        etTitle = (EditText)view.findViewById(R.id.etTitle);
        etDate = (EditText)view.findViewById(R.id.etDate);
        etTime= (EditText)view.findViewById(R.id.etTime);
        etCategory = (EditText)view.findViewById(R.id.etCategory);

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.btmNavigation);

        // MainActivity의 네비게이션 리스너를 프래그먼트에서 사용
        if (getActivity() instanceof MainActivity) {
            bottomNavigationView.setOnNavigationItemSelectedListener(((MainActivity) getActivity()).new BottomNavigationItemSelectedListener());
        }

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 'date' EditText 초기화
        EditText date = view.findViewById(R.id.etDate);  // 'view'는 onViewCreated 메서드에서 전달받은 매개변수입니다.
        EditText time = view.findViewById(R.id.etTime);

        // 현재 날짜 가져오기
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // DatePickerDialog 설정
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
            }
        }, mYear, mMonth, mDay);

        // 'date' EditText 클릭 시 DatePickerDialog 표시
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // TimePickerDialog 설정
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
            new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // 한국식 시간 형식으로 설정 (예: 오전 10:30)
                String amPm = (hourOfDay < 12) ? "오전" : "오후";
                int hour = (hourOfDay > 12) ? hourOfDay - 12 : hourOfDay;
                if (hour == 0) hour = 12;  // 0시는 12시로 표시
                time.setText(amPm + " " + hour + ":" + String.format("%02d", minute));
            }
        }, mHour, mMinute, false);  // 24시간 형식을 사용하지 않으려면 false로 설정

        // time EditText 클릭 시 TimePickerDialog 표시
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });




    }
}
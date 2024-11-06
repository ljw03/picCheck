package kiwu.android.piccheckstart.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import kiwu.android.piccheckstart.R;
import kiwu.android.piccheckstart.controller.ListCategoryController;
import kiwu.android.piccheckstart.model.TaskModel;

public class ListFragment extends Fragment {

    private FloatingActionButton fabCreate;
    private ListCategoryController categoryController;


    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("param1");
            String mParam2 = getArguments().getString("param2");
            // 전달받은 인자 mParam1과 mParam2를 필요한 곳에서 사용
        }
    }

    private RecyclerView recycler;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // fragment_list 레이아웃을 불러와 View 객체로 반환
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        recycler = view.findViewById(R.id.rvTasks);

        // categoryController 및 fabCreate 초기화
        categoryController = new ListCategoryController();
        fabCreate = view.findViewById(R.id.fabCreate);

        // fabCreate 클릭 리스너 설정
        fabCreate.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).changeFragment(MainActivity.CREATE_FRAGMENT);
            }
        });

        // RecyclerView 설정
        setupRecyclerView(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        recycler.setHasFixedSize(true);

        LinearLayoutManager linear = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(linear);

        TaskModelAdapter adapter = new TaskModelAdapter();
        recycler.setAdapter(adapter);

        MainActivity activity = (MainActivity)getActivity();
        ArrayList<TaskModel> students = activity.retrieveAll();

        adapter.addtasks(students);
        adapter.notifyDataSetChanged();
    }

    private void setupRecyclerView(View view) {
        // RecyclerView와 어댑터 설정
        updateItems(); // 선택된 카테고리별로 목록 업데이트
    }

    private void updateItems() {
        String selectedCategory = categoryController.getSelectedCategory();
        List<String> items = filterItemsByCategory(selectedCategory);
        // 필터링된 항목을 RecyclerView 어댑터에 설정
        // 예: adapter.setItems(items);
    }

    private List<String> filterItemsByCategory(String category) {
        // 선택된 카테고리에 맞춰 항목 필터링 (임시로 빈 목록 반환)
        return new ArrayList<>(); // 빈 ArrayList 반환하여 기본 에러 방지
    }

    public ListFragment() {
        // Required empty public constructor
    }


}

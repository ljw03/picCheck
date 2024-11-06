package kiwu.android.piccheckstart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import kiwu.android.piccheckstart.R;
import kiwu.android.piccheckstart.controller.ListCategoryController;
import kiwu.android.piccheckstart.model.TaskModel;

public class ListFragment extends Fragment {

    private FloatingActionButton fabCreate;
    private ListCategoryController categoryController;

    private TaskModelAdapter adapter;
    private RecyclerView recyclerView;


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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // fragment_list 레이아웃을 불러와 View 객체로 반환
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = view.findViewById(R.id.rvTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TaskModelAdapter();
        recyclerView.setAdapter(adapter);

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
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToCameraCallback(getContext()));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        MainActivity activity = (MainActivity) getActivity();
        ArrayList<TaskModel> tasks = activity.retrieveAll();
        adapter.addTasks(tasks);
    }

    private class SwipeToCameraCallback extends ItemTouchHelper.Callback {

        private Context context;
        private Paint paint = new Paint();

        public SwipeToCameraCallback(Context context) {
            this.context = context;
        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(0, ItemTouchHelper.LEFT);  // 왼쪽으로 스와이프 활성화
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;  // 이동 동작은 사용하지 않음
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Toast.makeText(context, "카메라 메뉴를 열었습니다.", Toast.LENGTH_SHORT).show();

            // 원래 상태로 복구
            adapter.notifyItemChanged(position);
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                View itemView = viewHolder.itemView;
                float height = (float) itemView.getBottom() - (float) itemView.getTop();
                float width = height / 3;

                paint.setColor(Color.parseColor("#FF4081"));
                RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                c.drawRect(background, paint);

                paint.setColor(Color.WHITE);
                paint.setTextSize(40);
                float textWidth = paint.measureText("카메라");
                float textX = itemView.getRight() - width - textWidth / 2;
                float textY = itemView.getTop() + height / 2 + 15;
                c.drawText("카메라", textX, textY, paint);
            }
        }
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

    // 스와이프 동작을 위한 내부 클래스



}

package kiwu.android.piccheckstart.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.Manifest;
import kiwu.android.piccheckstart.R;
import kiwu.android.piccheckstart.controller.ListCategoryController;
import kiwu.android.piccheckstart.controller.SwipeController;
import kiwu.android.piccheckstart.model.TaskModel;

public class ListFragment extends Fragment {

    private FloatingActionButton fabCreate;
    private ListCategoryController categoryController;
    private TaskModelAdapter adapter;
    private RecyclerView rvTasks;
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int CAMERA_INTENT_CODE = 101;
    private Uri photoUri;


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
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        rvTasks = view.findViewById(R.id.rvTasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TaskModelAdapter();
        rvTasks.setAdapter(adapter);

        categoryController = new ListCategoryController();
        fabCreate = view.findViewById(R.id.fabCreate);

        fabCreate.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).changeFragment(MainActivity.CREATE_FRAGMENT);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity activity = (MainActivity) getActivity();
        ArrayList<TaskModel> tasks = activity.retrieveAll();
        adapter.setItems(tasks);
        adapter.notifyDataSetChanged();
    }



    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(requireContext(), "kiwu.android.piccheckstart.fileprovider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, CAMERA_INTENT_CODE);
            }
        }
    }

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, CAMERA_REQUEST_CODE);
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            Toast.makeText(getContext(), "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTasks = view.findViewById(R.id.rvTasks);

        adapter = new TaskModelAdapter();
        rvTasks.setAdapter(adapter);

        fabCreate = view.findViewById(R.id.fabCreate);
        fabCreate.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).changeFragment(MainActivity.CREATE_FRAGMENT);
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    // 스와이프 시 CameraFragment 실행
                    ((MainActivity) requireActivity()).launchCameraFragment();
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvTasks);
    }

    private RecyclerView.ViewHolder currentItemViewHolder = null;

//...

    private void setupRecyclerView() {
        binding.recyclerViewReview.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false)
        );
        binding.recyclerViewReview.setAdapter(mysosoReviewAdapter);

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(binding.recyclerViewReview);

        binding.recyclerViewReview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    private void setupRecyclerView() {

        binding.recyclerViewReview.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false)
        );
        binding.recyclerViewReview.setAdapter(mysosoReviewAdapter);

        SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            //이동
            @Override
            public void onLeftClicked(int pos) {
                navConroller.navigate(MysosoReviewFragmentDirections.actionMysosoReviewFragmentToShopGraph(
                        mysosoReviewAdapter.getReviewModels().get(pos).getStoreId()
                ));
            }

            //삭제
            @Override
            public void onRightClicked(int pos) {
                //안전을 위해서 다이얼로그 추가
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("정말 삭제하시겠습니까?")
                        .setNeutralButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                myReviewViewModel.deleteMyReview(
                                        ((HomeActivity) getActivity()).getLoginToken(),
                                        mysosoReviewAdapter.getReviewModels().get(pos).getStoreId(),
                                        pos,
                                        MysosoReviewFragment.this::onSuccess,
                                        MysosoReviewFragment.this::onFailedDelete,
                                        MysosoReviewFragment.this::onNetworkErrorDelete);
                            }
                        })
                        .setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }

            //제자리로
            @Override
            public void onReset(int pos) {
                mysosoReviewAdapter.notifyItemChanged(pos);
            }
        }, getResources());

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(binding.recyclerViewReview);

        binding.recyclerViewReview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

        //...
    }




}

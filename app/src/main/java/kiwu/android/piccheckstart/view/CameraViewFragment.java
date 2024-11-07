package kiwu.android.piccheckstart.view;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;

import kiwu.android.piccheckstart.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CameraViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String ARG_IMAGE_URI = "image_uri";
    private Uri imageUri;

    public static CameraViewFragment newInstance(String imageUri) {
        CameraViewFragment fragment = new CameraViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMAGE_URI, imageUri);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CameraViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CameraViewFragment newInstance(String param1, String param2) {
        CameraViewFragment fragment = new CameraViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private ImageView imageView;
    private ImageCapture imageCapture;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUri = Uri.parse(getArguments().getString(ARG_IMAGE_URI));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera_view, container, false);

        ImageView imageView = view.findViewById(R.id.viewFinder);
        // 미리보기 설정 (예: Glide를 사용하여 이미지 로드)

        Glide.with(this).load(imageUri).into(imageView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 취소 버튼 설정
        View cancelButton = view.findViewById(R.id.cmr_cancel_button);
        if (cancelButton != null) {
            cancelButton.setOnClickListener(v -> {
                // 취소 시 다시 촬영 시도 로직
                retryPhotoCapture();
            });
        } else {

        }

        // 저장 버튼 설정
        View saveButton = view.findViewById(R.id.cmr_save_button);
        if (saveButton != null) {
            saveButton.setOnClickListener(v -> {
                // 저장 시 할 일 완료 처리 로직
                markTaskAsCompleted();
            });
        } else {

        }
    }

    private void retryPhotoCapture() {
        // 사진 다시 촬영 로직
    }

    private void markTaskAsCompleted() {
        // 할 일을 완료로 표시하는 로직
    }


}
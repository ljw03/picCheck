package kiwu.android.piccheckstart.model;

import java.util.ArrayList;
import java.util.List;

public class ListCategoryModel {
    private String selectedCategory;
    private List<String> categories;

    public ListCategoryModel() {
        categories = new ArrayList<>();
        categories.add("전체");
        categories.add("학교");
        categories.add("취미");
        categories.add("건강");
    }

    // 현재 선택된 카테고리 반환
    public String getSelectedCategory() {
        return selectedCategory;
    }

    // 선택된 카테고리 설정
    public void setSelectedCategory(String category) {
        this.selectedCategory = category;
    }

    // 모든 카테고리 목록 반환
    public List<String> getCategories() {
        return categories;
    }

    // 새 카테고리 추가
    public void addCategory(String category) {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }

    // 카테고리 삭제
    public void removeCategory(String category) {
        categories.remove(category);
    }
}

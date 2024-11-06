package kiwu.android.piccheckstart.controller;

import java.util.List;

import kiwu.android.piccheckstart.model.ListCategoryModel;

public class ListCategoryController {
    private ListCategoryModel model;

    public ListCategoryController() {
        this.model = new ListCategoryModel();
    }

    // 현재 선택된 카테고리 반환
    public String getSelectedCategory() {
        return model.getSelectedCategory();
    }

    // 선택된 카테고리 설정
    public void setSelectedCategory(String category) {
        model.setSelectedCategory(category);
    }

    // 선택된 카테고리를 초기화하는 메서드
    public void clearSelection() {
        model.setSelectedCategory(null); // 선택 초기화
    }

    // 선택된 카테고리로 설정하는 메서드 추가
    public void selectCategory(String category) {
        setSelectedCategory(category); // model에서 선택된 카테고리 설정
    }

    // 모든 카테고리 반환
    public List<String> getCategories() {
        return model.getCategories();
    }

    // 새 카테고리 추가
    public void addCategory(String category) {
        model.addCategory(category);
    }

    // 카테고리 삭제
    public void removeCategory(String category) {
        model.removeCategory(category);
    }
}

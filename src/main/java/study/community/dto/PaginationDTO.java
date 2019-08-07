package study.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PaginationDTO
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/29 16:44
 * @Version 1.0
 **/
@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPrevious;
    private boolean showNext;
    private boolean showFirstPage;
    private boolean showEndPage;

    private Integer page;
    private Integer totalPage;

    private List<Integer> pages = new ArrayList<>();

    public void setPaginnation(Integer page, Integer totalPage) {
        this.totalPage = totalPage;
        this.page = page;


        pages.add(page);

        for (int i = 1; i <= 3; i++) {
            if ((page - i) > 0) {
                pages.add(0, page - i);
            }
            if ((page + i) <= totalPage) {
                pages.add(page + i);
            }
        }

        //<
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        //>
        if (page.equals(totalPage)) {
            showNext = false;
        } else {
            showNext = true;
        }

        //<<
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        //>>
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}

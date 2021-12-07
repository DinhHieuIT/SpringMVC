package dinhhieumvc.model;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public abstract class BaseModel {
	private Sort sort;
	private Integer page = 1;
	private Integer perPage = 3;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPerPage() {
		return perPage;
	}

	public void setPerPage(Integer perPage) {
		this.perPage = perPage;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}
    /* PageRequest of(int page, int size, Sort sort)
     * Return of(page, size, Sort.by(direction, properties));
     * Page zero-based page index, must not be negative.
     * Size the size of the page to be returned, must be greater than 0.
     * Phương thức này sẽ lấy ra được trang số page-1, mỗi page có perPage phần tử
     */
	public Pageable getPageable() {
		if (sort == null) {
			return PageRequest.of(page - 1, perPage);
		} else {
			return PageRequest.of(page - 1, perPage, sort);
		}
	}
}

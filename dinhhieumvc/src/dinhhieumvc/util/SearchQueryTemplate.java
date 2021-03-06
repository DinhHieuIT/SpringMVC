package dinhhieumvc.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class SearchQueryTemplate {
	private String sql;
	private String countSql;
	private Map<String, Object> parameterMap;
	private Pageable pageable;
	private Sort sort;

	public SearchQueryTemplate(String sql, String countSql, Pageable pageable) {
		this.sql = sql;
		this.countSql = countSql;
		this.pageable = pageable;
		this.sort = pageable.getSort();
	}
	

	public SearchQueryTemplate(String sql, String countSql) {
		super();
		this.sql = sql;
		this.countSql = countSql;
	}



	public String getSql() {
		return sql;
	}

	public String getSql(boolean sortable) {
		//Nếu đúng và sort khác null
		if (sortable && sort != null) {
			final StringBuilder orderString = new StringBuilder();
			/* getProperty : Nhận được trường cần sắp xếp thứ tự.
			 * getDirection() : Nhận được thông số sắp xếp tăng dần hay giảm dần.
			 * Chuyển thông tin từ dạng Sort Object sang dạng String
			 */
			sort.forEach(order -> {
				orderString.append(order.getProperty() + " " + order.getDirection().name() + ",");
			});
			if (orderString.length() > 0) {
	//Phương thức substring trả về chuỗi con với vị trí bắt đầu và vị trí kết thúc
				sql = sql + " ORDER BY " + orderString.substring(0, orderString.length() - 1);
			}
		}
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getCountSql() {
		return countSql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	public Map<String, ? extends Object> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<String, Object> parameterMap) {
		this.parameterMap = parameterMap;
	}

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	public void addParameter(String property, Object value) {
		if (parameterMap == null) {
			parameterMap = new HashMap<String, Object>();
		}
		parameterMap.put(property, value);
	}

	public void addOrder(Direction value, String... properties) {
		if (sort == null) {
			return;
		}
		sort = sort.and(Sort.by(value, properties));
	}
    //Đưa giá trị các parameter vào câu truy vấn.
	public <E> void setParameters(Query<E> query) {
		if (parameterMap == null) {
			return;
		}
		for (String key : parameterMap.keySet()) {
			if (parameterMap.get(key) instanceof java.util.Arrays) {
				query.setParameterList(key, (Object[]) parameterMap.get(key));
			} else if (parameterMap.get(key) instanceof Collection<?>) {
				query.setParameterList(key, (Collection<?>) parameterMap.get(key));
			} else {
				query.setParameter(key, parameterMap.get(key));
			}
		}
	}
   //Thiết lập giá trị trang bắt đầu và số lượng phần tử trên mỗi trang.
	public <E> void setPageable(Query<E> query) {
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
	}

}

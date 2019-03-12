package pico.erp.production.order;

import static org.springframework.util.StringUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.production.order.ProductionOrderView.Filter;
import pico.erp.shared.Public;
import pico.erp.shared.jpa.QueryDslJpaSupport;

@Service
@Public
@Transactional(readOnly = true)
@Validated
public class ProductionOrderQueryJpa implements ProductionOrderQuery {


  private final QProductionOrderEntity order = QProductionOrderEntity.productionOrderEntity;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private QueryDslJpaSupport queryDslJpaSupport;

  @Override
  public Page<ProductionOrderView> retrieve(Filter filter, Pageable pageable) {
    val query = new JPAQuery<ProductionOrderView>(entityManager);
    val select = Projections.bean(ProductionOrderView.class,
      order.id,
      order.code,
      order.itemId,
      order.processId,
      order.itemSpecCode,
      order.quantity,
      order.spareQuantity,
      order.progressedQuantity,
      order.erroredQuantity,
      order.unit,
      order.ordererId,
      order.accepterId,
      order.projectId,
      order.receiverId,
      order.receiveSiteId,
      order.receiveStationId,
      order.dueDate,
      order.committedDate,
      order.completedDate,
      order.acceptedDate,
      order.rejectedDate,
      order.canceledDate,
      order.status,
      order.createdBy,
      order.createdDate,
      order.preparedDate,
      order.estimatedPreparedDate
    );
    query.select(select);
    query.from(order);

    val builder = new BooleanBuilder();

    if (!isEmpty(filter.getCode())) {
      builder.and(order.code.value
        .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", filter.getCode(), "%")));
    }

    if (filter.getReceiverId() != null) {
      builder.and(order.receiverId.eq(filter.getReceiverId()));
    }

    if (filter.getOrdererId() != null) {
      builder.and(order.ordererId.eq(filter.getOrdererId()));
    }

    if (filter.getAccepterId() != null) {
      builder.and(order.accepterId.eq(filter.getAccepterId()));
    }

    if (filter.getProjectId() != null) {
      builder.and(order.projectId.eq(filter.getProjectId()));
    }

    if (filter.getItemId() != null) {
      builder.and(order.itemId.eq(filter.getItemId()));
    }

    if (filter.getStatuses() != null && !filter.getStatuses().isEmpty()) {
      builder.and(order.status.in(filter.getStatuses()));
    }

    if (filter.getStartDueDate() != null) {
      builder.and(order.dueDate.goe(filter.getStartDueDate()));
    }
    if (filter.getEndDueDate() != null) {
      builder.and(order.dueDate.loe(filter.getEndDueDate()));
    }

    query.where(builder);
    return queryDslJpaSupport.paging(query, pageable, select);
  }

  @Override
  public Page<ProductionOrderAwaitExecutionView> retrieve(ProductionOrderAwaitExecutionView.Filter filter,
    Pageable pageable) {
    val query = new JPAQuery<ProductionOrderAwaitExecutionView>(entityManager);
    val select = Projections.bean(ProductionOrderAwaitExecutionView.class,
      order.id,
      order.code,
      order.itemId,
      order.processId,
      order.itemSpecCode,
      order.quantity,
      order.spareQuantity,
      order.unit,
      order.ordererId,
      order.projectId,
      order.receiverId,
      order.receiveSiteId,
      order.receiveStationId,
      order.committedDate,
      order.acceptedDate,
      order.dueDate,
      order.createdDate,
      order.preparedDate,
      order.estimatedPreparedDate
    );
    query.select(select);
    query.from(order);

    val builder = new BooleanBuilder();

    builder.and(
      order.status.in(ProductionOrderStatusKind.PREPARED, ProductionOrderStatusKind.IN_PROGRESS));

    if (filter.getReceiverId() != null) {
      builder.and(order.receiverId.eq(filter.getReceiverId()));
    }

    if (filter.getOrdererId() != null) {
      builder.and(order.ordererId.eq(filter.getOrdererId()));
    }

    if (filter.getProjectId() != null) {
      builder.and(order.projectId.eq(filter.getProjectId()));
    }

    if (filter.getItemId() != null) {
      builder.and(
        order.itemId.eq(filter.getItemId())
      );
    }

    if (filter.getStartDueDate() != null) {
      builder.and(order.dueDate.goe(filter.getStartDueDate()));
    }
    if (filter.getEndDueDate() != null) {
      builder.and(order.dueDate.loe(filter.getEndDueDate()));
    }

    query.where(builder);
    return queryDslJpaSupport.paging(query, pageable, select);
  }

  @Override
  public Page<ProductionOrderAwaitAcceptView> retrieve(ProductionOrderAwaitAcceptView.Filter filter,
    Pageable pageable) {
    val query = new JPAQuery<ProductionOrderAwaitAcceptView>(entityManager);
    val select = Projections.bean(ProductionOrderAwaitAcceptView.class,
      order.id,
      order.code,
      order.itemId,
      order.processId,
      order.itemSpecCode,
      order.quantity,
      order.spareQuantity,
      order.unit,
      order.ordererId,
      order.projectId,
      order.receiverId,
      order.receiveSiteId,
      order.receiveStationId,
      order.committedDate,
      order.dueDate,
      order.preparedDate,
      order.estimatedPreparedDate
    );
    query.select(select);
    query.from(order);

    val builder = new BooleanBuilder();

    builder.and(order.status.eq(ProductionOrderStatusKind.COMMITTED));

    if (!isEmpty(filter.getCode())) {
      builder.and(order.code.value
        .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", filter.getCode(), "%")));
    }

    if (filter.getReceiverId() != null) {
      builder.and(order.receiverId.eq(filter.getReceiverId()));
    }

    if (filter.getOrdererId() != null) {
      builder.and(order.ordererId.eq(filter.getOrdererId()));
    }

    if (filter.getProjectId() != null) {
      builder.and(order.projectId.eq(filter.getProjectId()));
    }

    if (filter.getItemId() != null) {
      builder.and(order.itemId.eq(filter.getItemId()));
    }

    if (filter.getStartDueDate() != null) {
      builder.and(order.dueDate.goe(filter.getStartDueDate()));
    }
    if (filter.getEndDueDate() != null) {
      builder.and(order.dueDate.loe(filter.getEndDueDate()));
    }

    query.where(builder);
    return queryDslJpaSupport.paging(query, pageable, select);
  }
}

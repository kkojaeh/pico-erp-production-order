package pico.erp.production.order;

import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
interface ProductionOrderEntityRepository extends
  CrudRepository<ProductionOrderEntity, ProductionOrderId> {

  @Query("SELECT COUNT(o) FROM ProductionOrder o WHERE o.createdDate >= :begin AND o.createdDate <= :end")
  long countCreatedBetween(@Param("begin") OffsetDateTime begin, @Param("end") OffsetDateTime end);

}

@Repository
@Transactional
public class ProductionOrderRepositoryJpa implements ProductionOrderRepository {

  @Autowired
  private ProductionOrderEntityRepository repository;

  @Autowired
  private ProductionOrderMapper mapper;

  @Override
  public long countCreatedBetween(OffsetDateTime begin, OffsetDateTime end) {
    return repository.countCreatedBetween(begin, end);
  }

  @Override
  public ProductionOrder create(ProductionOrder plan) {
    val entity = mapper.jpa(plan);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(ProductionOrderId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(ProductionOrderId id) {
    return repository.exists(id);
  }

  @Override
  public Optional<ProductionOrder> findBy(ProductionOrderId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::jpa);
  }

  @Override
  public void update(ProductionOrder plan) {
    val entity = repository.findOne(plan.getId());
    mapper.pass(mapper.jpa(plan), entity);
    repository.save(entity);
  }
}

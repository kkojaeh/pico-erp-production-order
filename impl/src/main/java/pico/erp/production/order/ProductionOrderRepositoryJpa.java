package pico.erp.production.order;

import java.time.LocalDateTime;
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
  long countCreatedBetween(@Param("begin") LocalDateTime begin, @Param("end") LocalDateTime end);

}

@Repository
@Transactional
public class ProductionOrderRepositoryJpa implements ProductionOrderRepository {

  @Autowired
  private ProductionOrderEntityRepository repository;

  @Autowired
  private ProductionOrderMapper mapper;

  @Override
  public long countCreatedBetween(LocalDateTime begin, LocalDateTime end) {
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
    repository.deleteById(id);
  }

  @Override
  public boolean exists(ProductionOrderId id) {
    return repository.existsById(id);
  }

  @Override
  public Optional<ProductionOrder> findBy(ProductionOrderId id) {
    return repository.findById(id)
      .map(mapper::jpa);
  }

  @Override
  public void update(ProductionOrder plan) {
    val entity = repository.findById(plan.getId()).get();
    mapper.pass(mapper.jpa(plan), entity);
    repository.save(entity);
  }
}

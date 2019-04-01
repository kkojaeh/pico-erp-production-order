package pico.erp.production.order;

import kkojaeh.spring.boot.component.Give;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.production.order.ProductionOrderRequests.AcceptRequest;
import pico.erp.production.order.ProductionOrderRequests.CancelRequest;
import pico.erp.production.order.ProductionOrderRequests.CommitRequest;
import pico.erp.production.order.ProductionOrderRequests.CompleteRequest;
import pico.erp.production.order.ProductionOrderRequests.PlanRequest;
import pico.erp.production.order.ProductionOrderRequests.PrepareRequest;
import pico.erp.production.order.ProductionOrderRequests.ProgressRequest;
import pico.erp.production.order.ProductionOrderRequests.RejectRequest;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Give
@Transactional
@Validated
public class ProductionOrderServiceLogic implements ProductionOrderService {

  @Autowired
  private ProductionOrderRepository productionOrderRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProductionOrderMapper mapper;

  @Override
  public void accept(AcceptRequest request) {
    val productionOrder = productionOrderRepository.findBy(request.getId())
      .orElseThrow(ProductionOrderExceptions.NotFoundException::new);
    val response = productionOrder.apply(mapper.map(request));
    productionOrderRepository.update(productionOrder);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void cancel(CancelRequest request) {
    val productionOrder = productionOrderRepository.findBy(request.getId())
      .orElseThrow(ProductionOrderExceptions.NotFoundException::new);
    val response = productionOrder.apply(mapper.map(request));
    productionOrderRepository.update(productionOrder);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void commit(CommitRequest request) {
    val productionOrder = productionOrderRepository.findBy(request.getId())
      .orElseThrow(ProductionOrderExceptions.NotFoundException::new);
    val response = productionOrder.apply(mapper.map(request));
    productionOrderRepository.update(productionOrder);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void complete(CompleteRequest request) {
    val productionOrder = productionOrderRepository.findBy(request.getId())
      .orElseThrow(ProductionOrderExceptions.NotFoundException::new);
    val response = productionOrder.apply(mapper.map(request));
    productionOrderRepository.update(productionOrder);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public ProductionOrderData create(ProductionOrderRequests.CreateRequest request) {
    val productionOrder = new ProductionOrder();
    val response = productionOrder.apply(mapper.map(request));
    if (productionOrderRepository.exists(productionOrder.getId())) {
      throw new ProductionOrderExceptions.AlreadyExistsException();
    }
    val created = productionOrderRepository.create(productionOrder);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public boolean exists(ProductionOrderId id) {
    return productionOrderRepository.exists(id);
  }

  @Override
  public ProductionOrderData get(ProductionOrderId id) {
    return productionOrderRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(ProductionOrderExceptions.NotFoundException::new);
  }

  @Override
  public void plan(PlanRequest request) {
    val productionOrder = productionOrderRepository.findBy(request.getId())
      .orElseThrow(ProductionOrderExceptions.NotFoundException::new);
    val response = productionOrder.apply(mapper.map(request));
    productionOrderRepository.update(productionOrder);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void prepare(PrepareRequest request) {
    val productionOrder = productionOrderRepository.findBy(request.getId())
      .orElseThrow(ProductionOrderExceptions.NotFoundException::new);
    val response = productionOrder.apply(mapper.map(request));
    productionOrderRepository.update(productionOrder);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void progress(ProgressRequest request) {
    val productionOrder = productionOrderRepository.findBy(request.getId())
      .orElseThrow(ProductionOrderExceptions.NotFoundException::new);
    val response = productionOrder.apply(mapper.map(request));
    productionOrderRepository.update(productionOrder);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void reject(RejectRequest request) {
    val productionOrder = productionOrderRepository.findBy(request.getId())
      .orElseThrow(ProductionOrderExceptions.NotFoundException::new);
    val response = productionOrder.apply(mapper.map(request));
    productionOrderRepository.update(productionOrder);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void update(ProductionOrderRequests.UpdateRequest request) {
    val productionOrder = productionOrderRepository.findBy(request.getId())
      .orElseThrow(ProductionOrderExceptions.NotFoundException::new);
    val response = productionOrder.apply(mapper.map(request));
    productionOrderRepository.update(productionOrder);
    eventPublisher.publishEvents(response.getEvents());
  }
}

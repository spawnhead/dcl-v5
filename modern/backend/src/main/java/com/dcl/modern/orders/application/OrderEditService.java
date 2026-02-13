package com.dcl.modern.orders.application;

import com.dcl.modern.dev.CurrentUser;
import com.dcl.modern.dev.CurrentUserProvider;
import com.dcl.modern.orders.api.*;
import com.dcl.modern.orders.domain.Order;
import com.dcl.modern.orders.domain.OrderPayment;
import com.dcl.modern.orders.domain.OrderPaySum;
import com.dcl.modern.orders.domain.OrderProduce;
import com.dcl.modern.orders.infrastructure.OrderLookupsRepository;
import com.dcl.modern.orders.infrastructure.OrderPaymentRepository;
import com.dcl.modern.orders.infrastructure.OrderPaySumRepository;
import com.dcl.modern.orders.infrastructure.OrderProduceRepository;
import com.dcl.modern.orders.infrastructure.OrderRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Order create/edit use cases. Legacy: OrderAction input, edit, process.
 * CONTRACTS: docs/screens/order_edit/CONTRACTS.md. Postgres-only.
 */
@Service
public class OrderEditService {

    private final OrderRepository orderRepository;
    private final OrderProduceRepository produceRepository;
    private final OrderPaymentRepository paymentRepository;
    private final OrderPaySumRepository paySumRepository;
    private final OrderLookupsRepository lookupsRepository;

    @org.springframework.beans.factory.annotation.Autowired(required = false)
    private CurrentUserProvider currentUserProvider;

    public OrderEditService(OrderRepository orderRepository,
                            OrderProduceRepository produceRepository,
                            OrderPaymentRepository paymentRepository,
                            OrderPaySumRepository paySumRepository,
                            OrderLookupsRepository lookupsRepository) {
        this.orderRepository = orderRepository;
        this.produceRepository = produceRepository;
        this.paymentRepository = paymentRepository;
        this.paySumRepository = paySumRepository;
        this.lookupsRepository = lookupsRepository;
    }

    @Transactional(readOnly = true)
    public OrderEditOpenResponse open(Optional<Integer> ordId) {
        List<LookupItemDto> contractors = withEmpty(lookupsRepository.getContractors());
        List<LookupItemDto> sellers = withEmpty(lookupsRepository.getSellers());
        List<LookupItemDto> currencies = withEmpty(lookupsRepository.getCurrencies());
        List<LookupItemDto> stuffCategories = withEmpty(lookupsRepository.getStuffCategories());
        List<LookupItemDto> blanks = lookupsRepository.getBlanks();
        if (blanks.isEmpty()) {
            blanks = List.of(new LookupItemDto("1", "Order default"));
        } else {
            blanks = withEmpty(blanks);
        }
        List<LookupItemDto> contracts = lookupsRepository.getContracts(null);
        List<LookupItemDto> specifications = lookupsRepository.getSpecifications(null, null);
        OrderEditOpenResponse.OrderEditLookupsDto lookups = new OrderEditOpenResponse.OrderEditLookupsDto(
            contractors, sellers, currencies, stuffCategories, withEmpty(blanks),
            withEmpty(contracts), withEmpty(specifications)
        );

        Optional<CurrentUser> user = currentUserProvider != null
            ? Optional.of(currentUserProvider.getCurrentUser())
            : Optional.empty();
        OrderEditOpenResponse.OrderRoleFlagsDto roleFlags = roleFlags(user);
        boolean formReadOnly = formReadOnly(ordId, user);

        String defaultBlankId = blanks.stream()
            .filter(b -> b.id() != null && !b.id().isBlank())
            .findFirst()
            .map(LookupItemDto::id)
            .orElse("1");

        if (ordId.isEmpty()) {
            OrderEditOpenResponse.OrderHeaderDto header = new OrderEditOpenResponse.OrderHeaderDto(
                null, null, LocalDate.now(),
                null, null, null, null, defaultBlankId, null,
                null, null, null,
                null, null, null, null,
                null, null, null, null, null, null, null,
                null, null, null, null
            );
            List<OrderEditOpenResponse.OrderPaymentRowDto> defaultPayments = List.of(
                new OrderEditOpenResponse.OrderPaymentRowDto(null, BigDecimal.valueOf(100), null, null)
            );
            List<OrderEditOpenResponse.OrderPaySumRowDto> defaultPaySums = List.of(
                new OrderEditOpenResponse.OrderPaySumRowDto(null, null, null)
            );
            return new OrderEditOpenResponse(header, List.of(), defaultPayments, defaultPaySums, lookups, roleFlags, formReadOnly);
        }

        Order order = orderRepository.findById(ordId.get()).orElseThrow();
        List<OrderProduce> produces = produceRepository.findByOrder_IdOrderById(order.getId());
        List<OrderPayment> payments = paymentRepository.findByOrder_IdOrderById(order.getId());
        List<OrderPaySum> paySums = paySumRepository.findByOrder_IdOrderById(order.getId());
        OrderEditOpenResponse.OrderHeaderDto header = toHeaderDto(order);
        List<OrderEditOpenResponse.OrderProduceRowDto> produceRows = produces.stream()
            .map(this::toProduceRowDto)
            .toList();
        List<OrderEditOpenResponse.OrderPaymentRowDto> paymentRows = payments.stream()
            .map(this::toPaymentRowDto)
            .toList();
        List<OrderEditOpenResponse.OrderPaySumRowDto> paySumRows = paySums.stream()
            .map(this::toPaySumRowDto)
            .toList();
        if (paymentRows.isEmpty()) {
            paymentRows = List.of(new OrderEditOpenResponse.OrderPaymentRowDto(null, BigDecimal.valueOf(100), null, null));
        }
        if (paySumRows.isEmpty()) {
            paySumRows = List.of(new OrderEditOpenResponse.OrderPaySumRowDto(null, null, null));
        }
        return new OrderEditOpenResponse(header, produceRows, paymentRows, paySumRows, lookups, roleFlags, formReadOnly);
    }

    @Transactional
    public OrderEditSaveResponse save(OrderEditSaveRequest req) {
        Order order;
        if (req.isNewDoc()) {
            order = new Order();
            order.setOrdDate(req.order().ordDate() != null ? req.order().ordDate() : LocalDate.now());
            String ordNum = req.order().ordNumber() != null && !req.order().ordNumber().isBlank()
                ? req.order().ordNumber() : "NEW";
            order.setNumber(truncate(ordNum, 15));
            order.setCreateDate(LocalDateTime.now());
            order.setEditDate(LocalDateTime.now());
            order.setContractorId(requireInt(req.order().contractorId(), "contractor"));
            order.setCurrencyId(requireInt(req.order().currencyId(), "currency"));
            order.setBlankId(req.order().blankId() != null ? req.order().blankId() : 1);
            order.setSellerForWhoId(requireInt(req.order().sellerForWhoId(), "sellerForWho"));
        } else {
            order = orderRepository.findById(requireInt(req.ordId(), "ordId"))
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + req.ordId()));
        }

        order.setContactPersonId(req.order().contactPersonId());
        order.setStuffCategoryId(req.order().stuffCategoryId());
        order.setContractorForId(req.order().contractorForId());
        order.setSpecificationId(req.order().specificationId());
        order.setComment(req.order().ordComment());
        order.setSentToProdDate(req.order().ordSentToProdDate());
        order.setReceivedConfDate(req.order().ordReceivedConfDate());
        order.setNumConf(req.order().ordNumConf());
        order.setDateConf(req.order().ordDateConf());
        order.setConfSentDate(req.order().ordConfSentDate());
        order.setReadyForDelivDate(req.order().ordReadyForDelivDate());
        order.setExecutedDate(req.order().ordExecutedDate());
        order.setPayCondition(req.order().ordPayCondition());
        order.setAddr(req.order().ordAddr());
        order.setDeliveryTerm(req.order().ordDeliveryTerm());
        order.setAddInfo(req.order().ordAddInfo());
        order.setEditDate(LocalDateTime.now());

        order = orderRepository.save(order);

        List<OrderProduce> existing = produceRepository.findByOrder_IdOrderById(order.getId());
        for (OrderProduce p : existing) {
            produceRepository.delete(p);
        }
        BigDecimal total = BigDecimal.ZERO;
        if (req.produces() != null) {
            for (OrderEditSaveRequest.OrderProduceSaveDto dto : req.produces()) {
                OrderProduce p = new OrderProduce();
                p.setOrder(order);
                p.setProduceName(dto.oprProduceName());
                p.setCatalogNum(dto.oprCatalogNum());
                p.setCount(dto.oprCount());
                p.setPriceBrutto(dto.oprPriceBrutto());
                p.setDiscount(dto.oprDiscount());
                p.setPriceNetto(dto.oprPriceNetto());
                p.setComment(dto.oprComment());
                p.setDrpPrice(dto.drpPrice());
                if (dto.oprCount() != null && dto.oprPriceNetto() != null) {
                    p.setSumm(dto.oprCount().multiply(dto.oprPriceNetto()));
                    total = total.add(p.getSumm());
                }
                produceRepository.save(p);
            }
        }
        order.setSumm(total);
        orderRepository.save(order);

        List<OrderPayment> existingPayments = paymentRepository.findByOrder_IdOrderById(order.getId());
        for (OrderPayment p : existingPayments) {
            paymentRepository.delete(p);
        }
        List<OrderEditSaveRequest.OrderPaymentSaveDto> paymentsToSave = req.orderPayments() != null ? req.orderPayments() : List.of();
        for (OrderEditSaveRequest.OrderPaymentSaveDto dto : paymentsToSave) {
            OrderPayment p = new OrderPayment();
            p.setOrder(order);
            p.setOrpPercent(dto.orpPercent() != null ? dto.orpPercent() : BigDecimal.ZERO);
            p.setOrpSum(dto.orpSum() != null ? dto.orpSum() : BigDecimal.ZERO);
            p.setOrpDate(dto.orpDate());
            paymentRepository.save(p);
        }

        List<OrderPaySum> existingPaySums = paySumRepository.findByOrder_IdOrderById(order.getId());
        for (OrderPaySum s : existingPaySums) {
            paySumRepository.delete(s);
        }
        List<OrderEditSaveRequest.OrderPaySumSaveDto> paySumsToSave = req.orderPaySums() != null ? req.orderPaySums() : List.of();
        for (OrderEditSaveRequest.OrderPaySumSaveDto dto : paySumsToSave) {
            OrderPaySum s = new OrderPaySum();
            s.setOrder(order);
            s.setOpsSum(dto.opsSum() != null ? dto.opsSum() : BigDecimal.ZERO);
            s.setOpsDate(dto.opsDate());
            paySumRepository.save(s);
        }

        return new OrderEditSaveResponse(order.getId(), order.getNumber());
    }

    private static List<LookupItemDto> withEmpty(List<LookupItemDto> list) {
        List<LookupItemDto> out = new ArrayList<>();
        out.add(new LookupItemDto("", "— Все —"));
        out.addAll(list);
        return out;
    }

    private static int requireInt(Integer v, String name) {
        if (v == null) throw new IllegalArgumentException("Missing required: " + name);
        return v;
    }

    private static String truncate(String s, int maxLen) {
        if (s == null || s.length() <= maxLen) return s;
        return s.substring(0, maxLen);
    }

    private OrderEditOpenResponse.OrderHeaderDto toHeaderDto(Order o) {
        return new OrderEditOpenResponse.OrderHeaderDto(
            o.getId(),
            o.getNumber(),
            o.getOrdDate(),
            o.getContractorId() != null ? String.valueOf(o.getContractorId()) : null,
            o.getContactPersonId() != null ? String.valueOf(o.getContactPersonId()) : null,
            o.getCurrencyId() != null ? String.valueOf(o.getCurrencyId()) : null,
            o.getStuffCategoryId() != null ? String.valueOf(o.getStuffCategoryId()) : null,
            o.getBlankId() != null ? String.valueOf(o.getBlankId()) : null,
            o.getSellerForWhoId() != null ? String.valueOf(o.getSellerForWhoId()) : null,
            null,
            null,
            o.getSpecificationId() != null ? String.valueOf(o.getSpecificationId()) : null,
            o.getBlock(),
            o.getAnnul(),
            o.getSumm(),
            o.getComment(),
            o.getSentToProdDate(),
            o.getReceivedConfDate(),
            o.getNumConf(),
            o.getDateConf(),
            o.getConfSentDate(),
            o.getExecutedDate(),
            o.getReadyForDelivDate(),
            o.getPayCondition(),
            o.getAddr(),
            o.getDeliveryTerm(),
            o.getAddInfo()
        );
    }

    private OrderEditOpenResponse.OrderProduceRowDto toProduceRowDto(OrderProduce p) {
        return new OrderEditOpenResponse.OrderProduceRowDto(
            p.getId(),
            p.getProduceName(),
            p.getCatalogNum(),
            p.getCount(),
            p.getPriceBrutto(),
            p.getDiscount(),
            p.getPriceNetto(),
            p.getSumm(),
            p.getComment(),
            p.getDrpPrice()
        );
    }

    private OrderEditOpenResponse.OrderPaymentRowDto toPaymentRowDto(OrderPayment p) {
        return new OrderEditOpenResponse.OrderPaymentRowDto(
            p.getId(),
            p.getOrpPercent(),
            p.getOrpSum(),
            p.getOrpDate()
        );
    }

    private OrderEditOpenResponse.OrderPaySumRowDto toPaySumRowDto(OrderPaySum s) {
        return new OrderEditOpenResponse.OrderPaySumRowDto(
            s.getId(),
            s.getOpsSum(),
            s.getOpsDate()
        );
    }

    private OrderEditOpenResponse.OrderRoleFlagsDto roleFlags(Optional<CurrentUser> user) {
        if (user.isEmpty()) {
            return new OrderEditOpenResponse.OrderRoleFlagsDto(false, false, false, false, false);
        }
        List<String> roles = user.get().roles() != null ? user.get().roles() : List.of();
        return new OrderEditOpenResponse.OrderRoleFlagsDto(
            roles.contains("admin"),
            roles.contains("economist"),
            roles.contains("logist"),
            roles.contains("manager"),
            roles.contains("userInLithuania")
        );
    }

    private boolean formReadOnly(Optional<Integer> ordId, Optional<CurrentUser> user) {
        if (user.isPresent() && user.get().roles() != null && user.get().roles().contains("userInLithuania")) {
            return true;
        }
        if (ordId.isEmpty()) return false;
        Order order = orderRepository.findById(ordId.get()).orElse(null);
        return order != null && order.getBlock() != null && order.getBlock() == 1;
    }
}

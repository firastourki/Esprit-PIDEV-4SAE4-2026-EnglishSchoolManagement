package tn.esprit.kamel_backend_microsevices.service;
import java.util.List;
import org.springframework.stereotype.Service;
import tn.esprit.kamel_backend_microsevices.entitiy.PaymentEntity;
import tn.esprit.kamel_backend_microsevices.repository.PaymentRepository;


@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public PaymentEntity create(PaymentEntity p) {
        p.setPaymentId(null); // safety
        return repository.save(p);
    }

    public List<PaymentEntity> findAll() {
        return repository.findAll();
    }

    public PaymentEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + id));
    }

    public PaymentEntity update(Long id, PaymentEntity payload) {
        PaymentEntity existing = findById(id);
        existing.setAmount(payload.getAmount());
        existing.setMethod(payload.getMethod());
        existing.setStatus(payload.getStatus());
        existing.setDate(payload.getDate());
        existing.setStudentId(payload.getStudentId());
        existing.setCourseId(payload.getCourseId());
        existing.setEnrollmentId(payload.getEnrollmentId());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<PaymentEntity> findByStudent(Long studentId) {
        return repository.findByStudentId(studentId);
    }
}
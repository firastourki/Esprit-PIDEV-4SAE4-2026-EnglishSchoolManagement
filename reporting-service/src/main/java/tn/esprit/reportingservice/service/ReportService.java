package tn.esprit.reportingservice.service;

import tn.esprit.reportingservice.entity.Report;
import tn.esprit.reportingservice.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    public Report create(Report r) {
        return repository.save(r);
    }

    public List<Report> getAll() {
        return repository.findAll();
    }

    public Report getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Report update(Long id, Report r) {
        Report existing = repository.findById(id).orElseThrow();
        existing.setTitle(r.getTitle());
        existing.setDescription(r.getDescription());
        existing.setTotalRevenue(r.getTotalRevenue());
        existing.setCreatedDate(r.getCreatedDate());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

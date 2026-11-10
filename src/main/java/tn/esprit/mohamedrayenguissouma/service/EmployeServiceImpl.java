package tn.esprit.mohamedrayenguissouma.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedrayenguissouma.domain.Employe;
import tn.esprit.mohamedrayenguissouma.repository.EmployeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor // injection par constructeur de l'attribut final employeRepository
public class EmployeServiceImpl implements IEmployeService {

    private final EmployeRepository employeRepository;

    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    @Override
    public Employe findById(Long idEmploye) {
        return employeRepository.findById(idEmploye).orElse(null);
    }

    @Override
    public Employe create(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public Employe update(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public void deleteById(Long idEmploye) {
        employeRepository.deleteById(idEmploye);
    }
}

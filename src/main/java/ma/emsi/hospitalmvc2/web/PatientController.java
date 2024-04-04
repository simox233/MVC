package ma.emsi.hospitalmvc2.web;

import lombok.AllArgsConstructor;
import ma.emsi.hospitalmvc2.entities.Patient;
import ma.emsi.hospitalmvc2.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;
    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "10") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String kw) {
        // Validate page number


        // Perform search with pagination
        Page<Patient> patientPage = patientRepository.findByNomContains(kw, PageRequest.of(page, size));

        // Error handling if patientPage is null or not found

        // Add attributes to model
        model.addAttribute("listPatients", patientPage.getContent());
        // Store the number of pages in an array
        model.addAttribute("pages", new int[patientPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", kw);
        return "patients";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id") Long id,
                         @RequestParam(name = "keyword",defaultValue = "") String keyword,
                         @RequestParam(name = "page",defaultValue = "0") int page){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }



}

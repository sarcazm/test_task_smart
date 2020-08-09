package converter.controller;

import converter.dom.DomValute;
import converter.helper.Helper;
import converter.model.Record;
import converter.model.Value;
import converter.model.Valute;
import converter.service.RecordService;
import converter.service.UserService;
import converter.service.ValueService;
import converter.service.ValuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ConverterController {

    private final DomValute domValute;
    private final ValueService valueService;
    private final ValuteService valuteService;
    private final UserService userService;
    private final RecordService recordService;
    private final Helper helper;

    @Autowired
    public ConverterController(DomValute domValute, ValueService valueService, ValuteService valuteService, Helper helper, UserService userService, RecordService recordService) {
        this.domValute = domValute;
        this.valueService = valueService;
        this.valuteService = valuteService;
        this.userService = userService;
        this.recordService = recordService;
        this.helper = helper;
    }

    @GetMapping("/")
    public String rootMain(){
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(Model model, HttpServletRequest request) {
        if ((valueService.findMaxDate() == null) || (!valueService.findMaxDate().isEqual(LocalDate.now()))){
            Map<Valute, Value> valuteValueMap = domValute.getAllValuteAndValueFromXml();
            helper.recordValuteAndValue(valuteValueMap);
        }
        List<Valute> valutes = valuteService.findAll();
        model.addAttribute("valutes", valutes);
        String username = request.getUserPrincipal().getName();
        List<Record> records = recordService.findAllByUser(userService.findByUsername(username));
        model.addAttribute("records", records);
        if (model.getAttribute("sum") == null){
            model.addAttribute("sum", 0.0);
        }

        return "main";
    }

    @PostMapping("/convert")
    public String convert(@RequestParam String from, @RequestParam String to, @RequestParam String qty, Model model, HttpServletRequest request){
        Record record = helper.convert(from, to, Integer.parseInt(qty));
        String username = request.getUserPrincipal().getName();
        record.setUser(userService.findByUsername(username));
        recordService.save(record);
        List<Valute> valutes = valuteService.findAll();
        model.addAttribute("valutes", valutes);
        List<Record> records = recordService.findAllByUser(userService.findByUsername(username));
        model.addAttribute("records", records);
        model.addAttribute("sum", record.getSum());
        return "main";
    }

    @GetMapping("/find")
    public String findByParams(@RequestParam String from, @RequestParam String to, @RequestParam String calendar, Model model){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(calendar, formatter);
        List<Valute> valutes = valuteService.findAll();
        model.addAttribute("valutes", valutes);
        List<Record> records = recordService.findByParams(date, valuteService.findByCharCode(from), valuteService.findByCharCode(to));
        model.addAttribute("records", records);
        return "/main";
    }

}

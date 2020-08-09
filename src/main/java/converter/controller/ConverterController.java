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
        return "main";
    }

    @PostMapping("/convert")
    public String convert(@RequestParam String from, @RequestParam String to, @RequestParam String qty, Model model, HttpServletRequest request){
        /*int qtyInt;
        try{
            qtyInt = Integer.parseInt(qty);
        }catch (NumberFormatException ex){
            throw new NumberFormatException("");
        }
        model.addAttribute("qty", qtyInt);*/
        Record record = helper.convert(from, to, Integer.parseInt(qty));
        String username = request.getUserPrincipal().getName();
        record.setUser(userService.findByUsername(username));
        recordService.save(record);
        //List<Record> records = recordService.findAll();
        //model.addAttribute("records", records);
        return "redirect:/main";
    }

    @GetMapping("/find")
    public String findByParams(@RequestParam String from, @RequestParam String to, @RequestParam String calendar, Model model){


        return "redirect:/main";
    }

}

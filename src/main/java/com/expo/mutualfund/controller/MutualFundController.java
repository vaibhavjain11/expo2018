package com.expo.mutualfund.controller;

import com.expo.mutualfund.dao.MutualFund;
import com.expo.mutualfund.dao.MutualFundRepository;
import com.expo.mutualfund.dao.Price;
import com.expo.mutualfund.dao.TempFund;
import com.expo.mutualfund.payload.FundRequest;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app")
public class MutualFundController {

    @Autowired
    MutualFundRepository repository;

    @Autowired
    String filePath;

    @RequestMapping(method = RequestMethod.POST, value = "/funds")
    public ResponseEntity<List<MutualFund>> getFunds(@RequestBody List<String> names) {

        if (names == null || names.size() == 0) {
            return new ResponseEntity("Invalid input", HttpStatus.BAD_REQUEST);
        }

        List<MutualFund> funds = names.stream().map(name -> repository.findAllByName(name)).flatMap(List::stream)
                .collect(Collectors.toList());
//
//        Map<LocalDate, List<TempFund>> map = funds.stream().flatMap(fund -> fund.getPrices().stream().map(p -> {
//            TempFund f = new TempFund();
//            f.setFundName(fund.getName());
//            f.setDate(p.getDate());
//            return f;
//        })).collect(Collectors.groupingBy(f -> f.getDate()));
//
//        map.entrySet().stream().forEach(item -> {
//            System.out.println(item.getKey() + " " + item.getValue());
//        });

        return new ResponseEntity(funds, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/fund")
    public void insertRecords(@RequestBody FundRequest request) {

        Path path = Paths.get(filePath + request.getName());

        FileInputStream file;
        try {
            file = new FileInputStream(new File(path.toString()));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator rows = sheet.iterator();
            List<Price> prices = new ArrayList<>();
            while (rows.hasNext()) {


                Row row = (Row) rows.next();

                System.out.println("Row Number = " + row.getRowNum());
                if (row.getRowNum() == 0)
                    continue;
                if (row == null || row.getCell(0) == null || row.getCell(1) == null)
                    break;
                String s1 = row.getCell(0).getStringCellValue();
                String s2 = row.getCell(1).getStringCellValue();
                if (StringUtils.isEmpty(s1) || StringUtils.isEmpty(s2)) {
                    break;
                }
                LocalDate date = LocalDate.parse(s1,
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                double value = Double.valueOf(s2);

                Price price = new Price();

                price.setDate(date);
                price.setPrice(value);

                prices.add(price);
            }
            ;

            String name = request.getName().substring(0, request.getName().length() - 5);
            MutualFund fund = new MutualFund();
            fund.setName(name);
            fund.setPrices(prices);

            repository.insert(fund);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

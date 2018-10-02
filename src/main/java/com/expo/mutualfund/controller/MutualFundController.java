package com.expo.mutualfund.controller;

import com.expo.mutualfund.dao.*;
import com.expo.mutualfund.payload.FundRequest;
import com.expo.mutualfund.payload.MFRequest;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app")
@CrossOrigin
public class MutualFundController {

    @Autowired
    MutualFundRepository repository;

    @Autowired
    MutualFundOutputRepository mutualFundOutputRepository;

    @Autowired
    String filePath;

    AtomicInteger counter = new AtomicInteger(1);

    @RequestMapping(method = RequestMethod.POST, value = "/funds")
    public ResponseEntity<List<MutualFund>> getFunds(@RequestBody List<String> names) {

        if (names == null || names.size() == 0) {
            return new ResponseEntity("Invalid input",HttpStatus.BAD_REQUEST);
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

        return new ResponseEntity(funds,HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/fund")
    public ResponseEntity insertRecords(@RequestBody FundRequest request) {

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

              //  System.out.println("Row Number = " + row.getRowNum());
                if (row.getRowNum() == 0)
                    continue;
                if (row == null || row.getCell(0) == null || row.getCell(1) == null)
                    break;
                System.out.print("Row num : " + row.getRowNum());
                LocalDate date = null;
                double value;
                if (true) {
                    Date date1 = row.getCell(0).getDateCellValue();
                    double s2 = row.getCell(1).getNumericCellValue();
                if (StringUtils.isEmpty(date1)) {
                    break;
                }

                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

                  //  date1  = date1.substring(1,date1.length());
                    date = LocalDate.parse(dateFormat.format(date1),
                            DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    value = Double.valueOf(s2);
                }
//                else {
//                    Date date1 = row.getCell(0).getDateCellValue();
//                    double s2 = row.getCell(1).getNumericCellValue();
////                if (StringUtils.isEmpty(date1)) {
////                    break;
////                }
//
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//
//                    date = LocalDate.parse(dateFormat.format(date1),
//                            DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//                    value = Double.valueOf(s2);
//                }

                Price price = new Price();

                price.setDate(date);
                price.setPrice(value);

                prices.add(price);
            }


            String name = request.getName().substring(0, request.getName().length() - 5);
            MutualFund fund = new MutualFund();
            fund.setName(name);
            fund.setCounter(15);
            fund.setRisk(request.getRisk());
            fund.setPrices(prices);

            MutualFund o = repository.save(fund);

            if (o != null) {
                return new ResponseEntity("Success", HttpStatus.OK);
            } else {
                return new ResponseEntity("Failure", HttpStatus.BAD_REQUEST);
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new ResponseEntity("Failure", HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new ResponseEntity("Failure", HttpStatus.BAD_REQUEST);
        }


    }


    @RequestMapping(method = RequestMethod.POST, value = "/output")
    public ResponseEntity insertFundOutput(@RequestBody  MutualFundOutput request) {

        MutualFundOutput o = mutualFundOutputRepository.save(request);

        if(o != null) {
            return new ResponseEntity("Success", HttpStatus.OK);
        } else {
            return new ResponseEntity("Failure", HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "/result")
    public ResponseEntity getOutputFund(@RequestBody MFRequest request) {

        List<MutualFundOutput> outputList = mutualFundOutputRepository.findAllByDuration(request.getDuration());
        List<MutualFund> list = outputList.stream()
                .map(o -> repository.findByCounter(o.getCounter()))
                .collect(Collectors.toList());
        if(list != null)
         return new ResponseEntity(list,HttpStatus.OK);
        return new ResponseEntity("Failure",HttpStatus.BAD_REQUEST);
    }

}

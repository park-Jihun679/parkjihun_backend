package org.wireBarley.example.controller;

import io.swagger.v3.oas.annotations.Parameter;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.wireBarley.common.exception.BindingErrorException;
import org.wireBarley.example.dto.ExampleCreateDTO;
import org.wireBarley.example.dto.ExampleDTO;
import org.wireBarley.example.dto.ExampleFilterDTO;
import org.wireBarley.example.dto.ExampleUpdateDTO;
import org.wireBarley.example.service.ExampleService;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

  private final ExampleService exampleService;

  @GetMapping("/examples/{exampleId}")
  public ExampleDTO getExample(
      @Parameter(description = "Example ID", required = true) @PathVariable("exampleId") UUID exampleId){

    return exampleService.getExample(exampleId);
  }

  @GetMapping("/examples")
  public Page<ExampleDTO> getExampleList(
      @ModelAttribute ExampleFilterDTO filterDTO){

    return exampleService.getExampleList(filterDTO);
  }

  @PostMapping("/examples")
  public ExampleDTO createExample(
      @RequestBody ExampleCreateDTO createDTO, BindingResult bindingResult){

    if (bindingResult.hasErrors()) {
      throw new BindingErrorException(bindingResult);
    }

    return exampleService.createExample(createDTO);
  }

  @PutMapping("/examples/{exampleId}")
  public ExampleDTO updateExample(
      @Parameter(description = "Example ID", required = true) @PathVariable("exampleId") UUID exampleId,
      @RequestBody ExampleUpdateDTO updateDTO, BindingResult bindingResult){

    if (bindingResult.hasErrors()) {
      throw new BindingErrorException(bindingResult);
    }

    updateDTO.setExampleId(exampleId);
    return exampleService.updateExample(updateDTO);
  }

  @DeleteMapping("/examples/{exampleId}")
  public void deleteExample(
      @Parameter(description = "Example ID", required = true) @PathVariable("exampleId") UUID exampleId){

    exampleService.deleteExample(exampleId);
  }


}

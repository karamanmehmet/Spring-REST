package com.cybertek.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.cybertek.model.Product;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clientBuilder")
public class RESTClientControllerWebClient {

	private WebClient webClient;

	private final String BASE_API = "/products";
	private final String BASE_API_BY_ID = "/products/{id}";

	@Autowired
	public RESTClientControllerWebClient(@Qualifier("webClient-Builder") WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.build();
	}

	@DeleteMapping("/{id}")
	public List<Product> delete(@PathVariable("id") Long id) {
		return webClient.delete().uri(BASE_API_BY_ID, id)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).retrieve().bodyToMono(List.class)
				.block();

	}

	@PutMapping("/{id}")
	public List<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {

		return webClient.put().uri(BASE_API_BY_ID, id)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(Mono.just(product), Product.class).retrieve().bodyToMono(List.class).block();
	}

	@PostMapping
	public List<Product> createProduct(@RequestBody Product product) {

		return webClient.post().uri(BASE_API).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(Mono.just(product), Product.class).retrieve().bodyToMono(List.class).block();

	}

	@GetMapping
	public List<Product> getProducts() {

		return webClient.get().uri(BASE_API).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.retrieve().bodyToMono(List.class).block();

	}

	@GetMapping(value = "/{id}")
	public Product getProduct(@PathVariable("id") Long id) {

		return webClient.get().uri(BASE_API_BY_ID, id).retrieve().bodyToMono(Product.class).block();

	}

}

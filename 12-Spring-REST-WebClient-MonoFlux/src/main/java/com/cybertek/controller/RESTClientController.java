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

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@RestController
@RequestMapping("/client")
public class RESTClientController {

	private WebClient webclient;

	private final String BASE_API = "http://localhost:8080/api/products";
	private final String BASE_API_BY_ID = "http://localhost:8080/api/products/{id}";

	@Autowired
	public RESTClientController(@Qualifier("webClient-Create") WebClient webclient) {
		this.webclient = webclient;
	}

	@DeleteMapping("/{id}")
	public Flux<Product> delete(@PathVariable("id") Long id) {
		return webclient.delete().uri(BASE_API_BY_ID, id)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).retrieve()
				.bodyToFlux(Product.class);

	}

	@PutMapping("/{id}")
	public Flux<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {

		return webclient.put().uri(BASE_API_BY_ID, id)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(Mono.just(product), Product.class).retrieve().bodyToFlux(Product.class);
	}

	@PostMapping
	public Flux<Product> createProduct(@RequestBody Product product) {

		return webclient.post().uri(BASE_API).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(Mono.just(product), Product.class).retrieve().bodyToFlux(Product.class);

	}

	@GetMapping
	public Flux<Product> getProducts() {

		return webclient.get().uri(BASE_API).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.retrieve().bodyToFlux(Product.class);

	}

	@GetMapping(value = "/{id}")
	public Mono<Product> getProduct(@PathVariable("id") Long id) {

		return webclient.get().uri(BASE_API_BY_ID, id).retrieve().bodyToMono(Product.class);

	}

}

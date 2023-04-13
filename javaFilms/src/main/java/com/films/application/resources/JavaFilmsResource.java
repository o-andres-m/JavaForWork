package com.films.application.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.Value;

@RestController
@RequestMapping("/")
public class JavaFilmsResource {

	@Value
	public static class CatalogoResources {
		
		String name = "JavaFilmsMicroservice";
		
		@Value
		public class CatalogoLinks {
			public class Href {
				private String href;
				public String getHref() { return href; }
				public Href(String path) {
					href = ServletUriComponentsBuilder.fromCurrentRequest().path(path).toUriString();
				}
			}
			private Href self = new Href("");
			private Href actors = new Href("/api/actors/v1");
			private Href films = new Href("/api/films/v1");
			private Href categories = new Href("/api/category/v1");
			private Href languages = new Href("/api/language/v1");
			private Href catalogue = new Href("/api/catalogue/v1");
			private Href swagger = new Href("/open-api");
		}

		private CatalogoLinks _links = new CatalogoLinks();
	}

	@GetMapping("/")
	public ResponseEntity<CatalogoResources> getResources() {
		return ResponseEntity.ok().header("Content-Type", "application/hal+json").body(new CatalogoResources());
	}

}

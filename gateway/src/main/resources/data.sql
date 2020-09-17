INSERT INTO public.roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO public.roles (name) VALUES ('ROLE_USER');

INSERT INTO public."user" (email, password) VALUES ('admin@gmail.com', '$2a$10$MdAAt0W8RzU7GBFB/umkr.igtmI2WypIgtnSbRft2HGnnVGgBvjne');
INSERT INTO public."user" (email, password) VALUES ('test@gmail.com', '$2a$10$41ns7PIeNwaSiQQ..XhIBO0iCGGOXIdLZ7hi9mQsB3IlJ.GFUAQdG');
INSERT INTO public."user" (email, password) VALUES ('autretest@gmail.com', '$2a$10$S40ZeBMbGYzWSqfaXlQOWuD9KcarnV6oDmRXx9gJvPGZldFywVEkG');

INSERT INTO public.user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO public.user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO public.user_roles (user_id, role_id) VALUES (3, 2);

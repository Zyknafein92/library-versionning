INSERT INTO public.roles VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.roles VALUES (2, 'ROLE_USER');

INSERT INTO public."user" VALUES (1, 'admin@gmail.com', '$2a$10$MdAAt0W8RzU7GBFB/umkr.igtmI2WypIgtnSbRft2HGnnVGgBvjne');
INSERT INTO public."user" VALUES (2, 'test@gmail.com', '$2a$10$41ns7PIeNwaSiQQ..XhIBO0iCGGOXIdLZ7hi9mQsB3IlJ.GFUAQdG');
INSERT INTO public."user" VALUES (3, 'autretest@gmail.com', '$2a$10$S40ZeBMbGYzWSqfaXlQOWuD9KcarnV6oDmRXx9gJvPGZldFywVEkG');

INSERT INTO public.user_roles VALUES (1, 1);
INSERT INTO public.user_roles VALUES (2, 2);
INSERT INTO public.user_roles VALUES (3, 2);

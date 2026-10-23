--
-- PostgreSQL database dump
--

\restrict cRYchNSJDzM7sgl9gxPsNNLixEG7fj5kBEhRbcFJ5nts3x6QEvpNGX1vVoIe3jv

-- Dumped from database version 18.6
-- Dumped by pg_dump version 18.6

-- Started on 2026-10-23 14:03:48

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5064 (class 0 OID 19201)
-- Dependencies: 220
-- Data for Name: bike; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.bike VALUES (1, '2026-10-23 14:00:37.014196', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.014196');
INSERT INTO public.bike VALUES (2, '2026-10-23 14:00:37.120606', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.120606');
INSERT INTO public.bike VALUES (3, '2026-10-23 14:00:37.129133', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.129133');
INSERT INTO public.bike VALUES (4, '2026-10-23 14:00:37.136388', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.136388');
INSERT INTO public.bike VALUES (5, '2026-10-23 14:00:37.14443', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.14443');
INSERT INTO public.bike VALUES (6, '2026-10-23 14:00:37.153166', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.153166');
INSERT INTO public.bike VALUES (7, '2026-10-23 14:00:37.161687', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.161687');
INSERT INTO public.bike VALUES (8, '2026-10-23 14:00:37.169099', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.169099');
INSERT INTO public.bike VALUES (9, '2026-10-23 14:00:37.174178', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.174178');
INSERT INTO public.bike VALUES (10, '2026-10-23 14:00:37.178176', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.178176');
INSERT INTO public.bike VALUES (11, '2026-10-23 14:00:37.182352', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.182352');
INSERT INTO public.bike VALUES (12, '2026-10-23 14:00:37.189868', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.189868');
INSERT INTO public.bike VALUES (13, '2026-10-23 14:00:37.197876', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.197876');
INSERT INTO public.bike VALUES (14, '2026-10-23 14:00:37.206257', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.206257');
INSERT INTO public.bike VALUES (15, '2026-10-23 14:00:37.213777', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.213777');
INSERT INTO public.bike VALUES (16, '2026-10-23 14:00:37.220784', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.220784');
INSERT INTO public.bike VALUES (17, '2026-10-23 14:00:37.22858', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.22858');
INSERT INTO public.bike VALUES (18, '2026-10-23 14:00:37.235846', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.235846');
INSERT INTO public.bike VALUES (19, '2026-10-23 14:00:37.243685', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.243685');
INSERT INTO public.bike VALUES (20, '2026-10-23 14:00:37.252059', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.252059');
INSERT INTO public.bike VALUES (21, '2026-10-23 14:00:37.259789', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.259789');
INSERT INTO public.bike VALUES (22, '2026-10-23 14:00:37.267708', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.267708');
INSERT INTO public.bike VALUES (23, '2026-10-23 14:00:37.275406', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.275406');
INSERT INTO public.bike VALUES (24, '2026-10-23 14:00:37.282564', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.282564');
INSERT INTO public.bike VALUES (25, '2026-10-23 14:00:37.291557', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.291557');
INSERT INTO public.bike VALUES (26, '2026-10-23 14:00:37.298271', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.298271');
INSERT INTO public.bike VALUES (27, '2026-10-23 14:00:37.306366', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.306366');
INSERT INTO public.bike VALUES (28, '2026-10-23 14:00:37.313239', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.313239');
INSERT INTO public.bike VALUES (29, '2026-10-23 14:00:37.319738', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.319738');
INSERT INTO public.bike VALUES (30, '2026-10-23 14:00:37.323736', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.323736');
INSERT INTO public.bike VALUES (31, '2026-10-23 14:00:37.33025', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.33025');
INSERT INTO public.bike VALUES (32, '2026-10-23 14:00:37.33729', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.33729');
INSERT INTO public.bike VALUES (33, '2026-10-23 14:00:37.34418', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.34418');
INSERT INTO public.bike VALUES (34, '2026-10-23 14:00:37.35086', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.35086');
INSERT INTO public.bike VALUES (35, '2026-10-23 14:00:37.35863', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.35863');
INSERT INTO public.bike VALUES (36, '2026-10-23 14:00:37.365554', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.365554');
INSERT INTO public.bike VALUES (37, '2026-10-23 14:00:37.373446', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.373446');
INSERT INTO public.bike VALUES (38, '2026-10-23 14:00:37.38033', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.38033');
INSERT INTO public.bike VALUES (39, '2026-10-23 14:00:37.386285', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.386285');
INSERT INTO public.bike VALUES (40, '2026-10-23 14:00:37.393396', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.393396');
INSERT INTO public.bike VALUES (41, '2026-10-23 14:00:37.400024', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.400024');
INSERT INTO public.bike VALUES (42, '2026-10-23 14:00:37.406986', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.406986');
INSERT INTO public.bike VALUES (43, '2026-10-23 14:00:37.414577', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.414577');
INSERT INTO public.bike VALUES (44, '2026-10-23 14:00:37.424595', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.424595');
INSERT INTO public.bike VALUES (45, '2026-10-23 14:00:37.432175', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.432175');
INSERT INTO public.bike VALUES (46, '2026-10-23 14:00:37.442692', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.442692');
INSERT INTO public.bike VALUES (47, '2026-10-23 14:00:37.450696', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.450696');
INSERT INTO public.bike VALUES (48, '2026-10-23 14:00:37.459755', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.459755');
INSERT INTO public.bike VALUES (49, '2026-10-23 14:00:37.468775', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.468775');
INSERT INTO public.bike VALUES (50, '2026-10-23 14:00:37.477333', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.477333');
INSERT INTO public.bike VALUES (51, '2026-10-23 14:00:37.485356', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.485356');
INSERT INTO public.bike VALUES (52, '2026-10-23 14:00:37.494355', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.494355');
INSERT INTO public.bike VALUES (53, '2026-10-23 14:00:37.50564', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.50564');
INSERT INTO public.bike VALUES (54, '2026-10-23 14:00:37.514089', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.514089');
INSERT INTO public.bike VALUES (55, '2026-10-23 14:00:37.521602', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.521602');
INSERT INTO public.bike VALUES (56, '2026-10-23 14:00:37.529614', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.529614');
INSERT INTO public.bike VALUES (57, '2026-10-23 14:00:37.53729', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.53729');
INSERT INTO public.bike VALUES (58, '2026-10-23 14:00:37.5448', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.5448');
INSERT INTO public.bike VALUES (59, '2026-10-23 14:00:37.55282', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.55282');
INSERT INTO public.bike VALUES (60, '2026-10-23 14:00:37.560782', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.560782');
INSERT INTO public.bike VALUES (61, '2026-10-23 14:00:37.568418', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.568418');
INSERT INTO public.bike VALUES (62, '2026-10-23 14:00:37.577024', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.577024');
INSERT INTO public.bike VALUES (63, '2026-10-23 14:00:37.585043', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.585043');
INSERT INTO public.bike VALUES (64, '2026-10-23 14:00:37.592153', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.592153');
INSERT INTO public.bike VALUES (65, '2026-10-23 14:00:37.601903', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.601903');
INSERT INTO public.bike VALUES (66, '2026-10-23 14:00:37.609424', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.610422');
INSERT INTO public.bike VALUES (67, '2026-10-23 14:00:37.617824', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.617824');
INSERT INTO public.bike VALUES (68, '2026-10-23 14:00:37.623061', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.623061');
INSERT INTO public.bike VALUES (69, '2026-10-23 14:00:37.628589', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.62959');
INSERT INTO public.bike VALUES (70, '2026-10-23 14:00:37.637113', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.637113');
INSERT INTO public.bike VALUES (71, '2026-10-23 14:00:37.644799', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.644799');
INSERT INTO public.bike VALUES (72, '2026-10-23 14:00:37.651412', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.651412');
INSERT INTO public.bike VALUES (73, '2026-10-23 14:00:37.659521', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.659521');
INSERT INTO public.bike VALUES (74, '2026-10-23 14:00:37.666586', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.666586');
INSERT INTO public.bike VALUES (75, '2026-10-23 14:00:37.674654', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.674654');
INSERT INTO public.bike VALUES (76, '2026-10-23 14:00:37.681729', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.681729');
INSERT INTO public.bike VALUES (77, '2026-10-23 14:00:37.689237', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.689237');
INSERT INTO public.bike VALUES (78, '2026-10-23 14:00:37.696312', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.696312');
INSERT INTO public.bike VALUES (79, '2026-10-23 14:00:37.703833', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.703833');
INSERT INTO public.bike VALUES (80, '2026-10-23 14:00:37.711057', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.711057');
INSERT INTO public.bike VALUES (81, '2026-10-23 14:00:37.717941', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.717941');
INSERT INTO public.bike VALUES (82, '2026-10-23 14:00:37.72548', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.72548');
INSERT INTO public.bike VALUES (83, '2026-10-23 14:00:37.729174', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.729174');
INSERT INTO public.bike VALUES (84, '2026-10-23 14:00:37.734117', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.734117');
INSERT INTO public.bike VALUES (85, '2026-10-23 14:00:37.737721', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.738717');
INSERT INTO public.bike VALUES (86, '2026-10-23 14:00:37.743413', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.743413');
INSERT INTO public.bike VALUES (87, '2026-10-23 14:00:37.752102', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.752102');
INSERT INTO public.bike VALUES (88, '2026-10-23 14:00:37.759539', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.759539');
INSERT INTO public.bike VALUES (89, '2026-10-23 14:00:37.766041', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.766041');
INSERT INTO public.bike VALUES (90, '2026-10-23 14:00:37.773652', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.773652');
INSERT INTO public.bike VALUES (91, '2026-10-23 14:00:37.780444', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.780444');
INSERT INTO public.bike VALUES (92, '2026-10-23 14:00:37.787099', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.787099');
INSERT INTO public.bike VALUES (93, '2026-10-23 14:00:37.793912', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.793912');
INSERT INTO public.bike VALUES (94, '2026-10-23 14:00:37.800921', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.800921');
INSERT INTO public.bike VALUES (95, '2026-10-23 14:00:37.808451', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.808451');
INSERT INTO public.bike VALUES (96, '2026-10-23 14:00:37.815514', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.815514');
INSERT INTO public.bike VALUES (97, '2026-10-23 14:00:37.822522', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.822522');
INSERT INTO public.bike VALUES (98, '2026-10-23 14:00:37.830082', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.830082');
INSERT INTO public.bike VALUES (99, '2026-10-23 14:00:37.837644', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.837644');
INSERT INTO public.bike VALUES (100, '2026-10-23 14:00:37.844751', 0, 'BANSEOK', 'AVAILABLE', '2026-10-23 14:00:37.844751');


--
-- TOC entry 5074 (class 0 OID 19275)
-- Dependencies: 230
-- Data for Name: member; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.member VALUES (1, '2026-10-23 13:58:54.16153', 'admin@example.com', 'admin', '관리자', '$2a$10$19g1QSF.OWGRNJrRknrzeOi3lt0wURbG9TNl33cKyR0iJYt7p2AX.', true, 'ADMIN', 'ACTIVE', '010-xxxx-xxxx', true, '2026-10-23 13:58:54.16153');
INSERT INTO public.member VALUES (2, '2026-10-23 14:01:49.350047', 'dummy1@example.com', 'dummyMan 1', '더미휴먼 1', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.350047');
INSERT INTO public.member VALUES (3, '2026-10-23 14:01:49.500135', 'dummy2@example.com', 'dummyMan 2', '더미휴먼 2', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.500135');
INSERT INTO public.member VALUES (4, '2026-10-23 14:01:49.514445', 'dummy3@example.com', 'dummyMan 3', '더미휴먼 3', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.514445');
INSERT INTO public.member VALUES (5, '2026-10-23 14:01:49.530501', 'dummy4@example.com', 'dummyMan 4', '더미휴먼 4', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.530501');
INSERT INTO public.member VALUES (6, '2026-10-23 14:01:49.536128', 'dummy5@example.com', 'dummyMan 5', '더미휴먼 5', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.536128');
INSERT INTO public.member VALUES (7, '2026-10-23 14:01:49.542753', 'dummy6@example.com', 'dummyMan 6', '더미휴먼 6', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.542753');
INSERT INTO public.member VALUES (8, '2026-10-23 14:01:49.551349', 'dummy7@example.com', 'dummyMan 7', '더미휴먼 7', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.551349');
INSERT INTO public.member VALUES (9, '2026-10-23 14:01:49.558547', 'dummy8@example.com', 'dummyMan 8', '더미휴먼 8', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.558547');
INSERT INTO public.member VALUES (10, '2026-10-23 14:01:49.56487', 'dummy9@example.com', 'dummyMan 9', '더미휴먼 9', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.56487');
INSERT INTO public.member VALUES (11, '2026-10-23 14:01:49.572632', 'dummy10@example.com', 'dummyMan 10', '더미휴먼 10', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.572632');
INSERT INTO public.member VALUES (12, '2026-10-23 14:01:49.579598', 'dummy11@example.com', 'dummyMan 11', '더미휴먼 11', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.579598');
INSERT INTO public.member VALUES (13, '2026-10-23 14:01:49.588134', 'dummy12@example.com', 'dummyMan 12', '더미휴먼 12', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.588134');
INSERT INTO public.member VALUES (14, '2026-10-23 14:01:49.595902', 'dummy13@example.com', 'dummyMan 13', '더미휴먼 13', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.595902');
INSERT INTO public.member VALUES (15, '2026-10-23 14:01:49.60352', 'dummy14@example.com', 'dummyMan 14', '더미휴먼 14', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.60352');
INSERT INTO public.member VALUES (16, '2026-10-23 14:01:49.610882', 'dummy15@example.com', 'dummyMan 15', '더미휴먼 15', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.610882');
INSERT INTO public.member VALUES (17, '2026-10-23 14:01:49.619209', 'dummy16@example.com', 'dummyMan 16', '더미휴먼 16', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.619209');
INSERT INTO public.member VALUES (18, '2026-10-23 14:01:49.625464', 'dummy17@example.com', 'dummyMan 17', '더미휴먼 17', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.625464');
INSERT INTO public.member VALUES (19, '2026-10-23 14:01:49.634045', 'dummy18@example.com', 'dummyMan 18', '더미휴먼 18', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.634045');
INSERT INTO public.member VALUES (20, '2026-10-23 14:01:49.640826', 'dummy19@example.com', 'dummyMan 19', '더미휴먼 19', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.640826');
INSERT INTO public.member VALUES (21, '2026-10-23 14:01:49.647898', 'dummy20@example.com', 'dummyMan 20', '더미휴먼 20', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.647898');
INSERT INTO public.member VALUES (22, '2026-10-23 14:01:49.655358', 'dummy21@example.com', 'dummyMan 21', '더미휴먼 21', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.655358');
INSERT INTO public.member VALUES (23, '2026-10-23 14:01:49.662519', 'dummy22@example.com', 'dummyMan 22', '더미휴먼 22', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.662519');
INSERT INTO public.member VALUES (24, '2026-10-23 14:01:49.670858', 'dummy23@example.com', 'dummyMan 23', '더미휴먼 23', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.670858');
INSERT INTO public.member VALUES (25, '2026-10-23 14:01:49.678007', 'dummy24@example.com', 'dummyMan 24', '더미휴먼 24', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.678007');
INSERT INTO public.member VALUES (26, '2026-10-23 14:01:49.687522', 'dummy25@example.com', 'dummyMan 25', '더미휴먼 25', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.687522');
INSERT INTO public.member VALUES (27, '2026-10-23 14:01:49.694513', 'dummy26@example.com', 'dummyMan 26', '더미휴먼 26', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.694513');
INSERT INTO public.member VALUES (28, '2026-10-23 14:01:49.7028', 'dummy27@example.com', 'dummyMan 27', '더미휴먼 27', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.7028');
INSERT INTO public.member VALUES (29, '2026-10-23 14:01:49.708003', 'dummy28@example.com', 'dummyMan 28', '더미휴먼 28', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.708003');
INSERT INTO public.member VALUES (30, '2026-10-23 14:01:49.712639', 'dummy29@example.com', 'dummyMan 29', '더미휴먼 29', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.712639');
INSERT INTO public.member VALUES (31, '2026-10-23 14:01:49.717918', 'dummy30@example.com', 'dummyMan 30', '더미휴먼 30', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.717918');
INSERT INTO public.member VALUES (32, '2026-10-23 14:01:49.724684', 'dummy31@example.com', 'dummyMan 31', '더미휴먼 31', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.724684');
INSERT INTO public.member VALUES (33, '2026-10-23 14:01:49.732236', 'dummy32@example.com', 'dummyMan 32', '더미휴먼 32', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.732236');
INSERT INTO public.member VALUES (34, '2026-10-23 14:01:49.739491', 'dummy33@example.com', 'dummyMan 33', '더미휴먼 33', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.739491');
INSERT INTO public.member VALUES (35, '2026-10-23 14:01:49.745847', 'dummy34@example.com', 'dummyMan 34', '더미휴먼 34', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.745847');
INSERT INTO public.member VALUES (36, '2026-10-23 14:01:49.753533', 'dummy35@example.com', 'dummyMan 35', '더미휴먼 35', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.753533');
INSERT INTO public.member VALUES (37, '2026-10-23 14:01:49.760907', 'dummy36@example.com', 'dummyMan 36', '더미휴먼 36', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.760907');
INSERT INTO public.member VALUES (38, '2026-10-23 14:01:49.769603', 'dummy37@example.com', 'dummyMan 37', '더미휴먼 37', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.769603');
INSERT INTO public.member VALUES (39, '2026-10-23 14:01:49.7766', 'dummy38@example.com', 'dummyMan 38', '더미휴먼 38', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.7766');
INSERT INTO public.member VALUES (40, '2026-10-23 14:01:49.78424', 'dummy39@example.com', 'dummyMan 39', '더미휴먼 39', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.78424');
INSERT INTO public.member VALUES (41, '2026-10-23 14:01:49.791603', 'dummy40@example.com', 'dummyMan 40', '더미휴먼 40', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.791603');
INSERT INTO public.member VALUES (42, '2026-10-23 14:01:49.798299', 'dummy41@example.com', 'dummyMan 41', '더미휴먼 41', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.798299');
INSERT INTO public.member VALUES (43, '2026-10-23 14:01:49.805771', 'dummy42@example.com', 'dummyMan 42', '더미휴먼 42', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.805771');
INSERT INTO public.member VALUES (44, '2026-10-23 14:01:49.812731', 'dummy43@example.com', 'dummyMan 43', '더미휴먼 43', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.812731');
INSERT INTO public.member VALUES (45, '2026-10-23 14:01:49.821254', 'dummy44@example.com', 'dummyMan 44', '더미휴먼 44', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.821254');
INSERT INTO public.member VALUES (46, '2026-10-23 14:01:49.828051', 'dummy45@example.com', 'dummyMan 45', '더미휴먼 45', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.828051');
INSERT INTO public.member VALUES (47, '2026-10-23 14:01:49.837021', 'dummy46@example.com', 'dummyMan 46', '더미휴먼 46', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.837021');
INSERT INTO public.member VALUES (48, '2026-10-23 14:01:49.84451', 'dummy47@example.com', 'dummyMan 47', '더미휴먼 47', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.84451');
INSERT INTO public.member VALUES (49, '2026-10-23 14:01:49.854038', 'dummy48@example.com', 'dummyMan 48', '더미휴먼 48', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.854038');
INSERT INTO public.member VALUES (50, '2026-10-23 14:01:49.861063', 'dummy49@example.com', 'dummyMan 49', '더미휴먼 49', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.861543');
INSERT INTO public.member VALUES (51, '2026-10-23 14:01:49.869593', 'dummy50@example.com', 'dummyMan 50', '더미휴먼 50', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.869593');
INSERT INTO public.member VALUES (52, '2026-10-23 14:01:49.876735', 'dummy51@example.com', 'dummyMan 51', '더미휴먼 51', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.876735');
INSERT INTO public.member VALUES (53, '2026-10-23 14:01:49.885587', 'dummy52@example.com', 'dummyMan 52', '더미휴먼 52', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.885587');
INSERT INTO public.member VALUES (54, '2026-10-23 14:01:49.890933', 'dummy53@example.com', 'dummyMan 53', '더미휴먼 53', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.890933');
INSERT INTO public.member VALUES (55, '2026-10-23 14:01:49.896006', 'dummy54@example.com', 'dummyMan 54', '더미휴먼 54', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.896006');
INSERT INTO public.member VALUES (56, '2026-10-23 14:01:49.903797', 'dummy55@example.com', 'dummyMan 55', '더미휴먼 55', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.903797');
INSERT INTO public.member VALUES (57, '2026-10-23 14:01:49.910799', 'dummy56@example.com', 'dummyMan 56', '더미휴먼 56', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.910799');
INSERT INTO public.member VALUES (58, '2026-10-23 14:01:49.919318', 'dummy57@example.com', 'dummyMan 57', '더미휴먼 57', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.919318');
INSERT INTO public.member VALUES (59, '2026-10-23 14:01:49.926641', 'dummy58@example.com', 'dummyMan 58', '더미휴먼 58', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.926641');
INSERT INTO public.member VALUES (60, '2026-10-23 14:01:49.93471', 'dummy59@example.com', 'dummyMan 59', '더미휴먼 59', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.93471');
INSERT INTO public.member VALUES (61, '2026-10-23 14:01:49.941874', 'dummy60@example.com', 'dummyMan 60', '더미휴먼 60', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.941874');
INSERT INTO public.member VALUES (62, '2026-10-23 14:01:49.952691', 'dummy61@example.com', 'dummyMan 61', '더미휴먼 61', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.952691');
INSERT INTO public.member VALUES (63, '2026-10-23 14:01:49.959899', 'dummy62@example.com', 'dummyMan 62', '더미휴먼 62', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.959899');
INSERT INTO public.member VALUES (64, '2026-10-23 14:01:49.970323', 'dummy63@example.com', 'dummyMan 63', '더미휴먼 63', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.970323');
INSERT INTO public.member VALUES (65, '2026-10-23 14:01:49.978846', 'dummy64@example.com', 'dummyMan 64', '더미휴먼 64', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.978846');
INSERT INTO public.member VALUES (66, '2026-10-23 14:01:49.986705', 'dummy65@example.com', 'dummyMan 65', '더미휴먼 65', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.986705');
INSERT INTO public.member VALUES (67, '2026-10-23 14:01:49.994322', 'dummy66@example.com', 'dummyMan 66', '더미휴먼 66', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:49.994322');
INSERT INTO public.member VALUES (68, '2026-10-23 14:01:50.003611', 'dummy67@example.com', 'dummyMan 67', '더미휴먼 67', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.003611');
INSERT INTO public.member VALUES (69, '2026-10-23 14:01:50.011209', 'dummy68@example.com', 'dummyMan 68', '더미휴먼 68', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.011209');
INSERT INTO public.member VALUES (70, '2026-10-23 14:01:50.019586', 'dummy69@example.com', 'dummyMan 69', '더미휴먼 69', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.019586');
INSERT INTO public.member VALUES (71, '2026-10-23 14:01:50.026588', 'dummy70@example.com', 'dummyMan 70', '더미휴먼 70', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.026588');
INSERT INTO public.member VALUES (72, '2026-10-23 14:01:50.035687', 'dummy71@example.com', 'dummyMan 71', '더미휴먼 71', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.035687');
INSERT INTO public.member VALUES (73, '2026-10-23 14:01:50.043022', 'dummy72@example.com', 'dummyMan 72', '더미휴먼 72', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.043022');
INSERT INTO public.member VALUES (74, '2026-10-23 14:01:50.054507', 'dummy73@example.com', 'dummyMan 73', '더미휴먼 73', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.054507');
INSERT INTO public.member VALUES (75, '2026-10-23 14:01:50.061964', 'dummy74@example.com', 'dummyMan 74', '더미휴먼 74', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.061964');
INSERT INTO public.member VALUES (76, '2026-10-23 14:01:50.070647', 'dummy75@example.com', 'dummyMan 75', '더미휴먼 75', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.070647');
INSERT INTO public.member VALUES (77, '2026-10-23 14:01:50.076655', 'dummy76@example.com', 'dummyMan 76', '더미휴먼 76', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.076655');
INSERT INTO public.member VALUES (78, '2026-10-23 14:01:50.083972', 'dummy77@example.com', 'dummyMan 77', '더미휴먼 77', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.083972');
INSERT INTO public.member VALUES (79, '2026-10-23 14:01:50.088982', 'dummy78@example.com', 'dummyMan 78', '더미휴먼 78', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.090021');
INSERT INTO public.member VALUES (80, '2026-10-23 14:01:50.094979', 'dummy79@example.com', 'dummyMan 79', '더미휴먼 79', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.094979');
INSERT INTO public.member VALUES (81, '2026-10-23 14:01:50.103021', 'dummy80@example.com', 'dummyMan 80', '더미휴먼 80', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.103021');
INSERT INTO public.member VALUES (82, '2026-10-23 14:01:50.110473', 'dummy81@example.com', 'dummyMan 81', '더미휴먼 81', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.110473');
INSERT INTO public.member VALUES (83, '2026-10-23 14:01:50.119125', 'dummy82@example.com', 'dummyMan 82', '더미휴먼 82', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.119125');
INSERT INTO public.member VALUES (84, '2026-10-23 14:01:50.126418', 'dummy83@example.com', 'dummyMan 83', '더미휴먼 83', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.126418');
INSERT INTO public.member VALUES (85, '2026-10-23 14:01:50.134898', 'dummy84@example.com', 'dummyMan 84', '더미휴먼 84', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.134898');
INSERT INTO public.member VALUES (86, '2026-10-23 14:01:50.142538', 'dummy85@example.com', 'dummyMan 85', '더미휴먼 85', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.142538');
INSERT INTO public.member VALUES (87, '2026-10-23 14:01:50.149547', 'dummy86@example.com', 'dummyMan 86', '더미휴먼 86', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.149547');
INSERT INTO public.member VALUES (88, '2026-10-23 14:01:50.156876', 'dummy87@example.com', 'dummyMan 87', '더미휴먼 87', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.156876');
INSERT INTO public.member VALUES (89, '2026-10-23 14:01:50.164074', 'dummy88@example.com', 'dummyMan 88', '더미휴먼 88', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.164074');
INSERT INTO public.member VALUES (90, '2026-10-23 14:01:50.171035', 'dummy89@example.com', 'dummyMan 89', '더미휴먼 89', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.171035');
INSERT INTO public.member VALUES (91, '2026-10-23 14:01:50.178061', 'dummy90@example.com', 'dummyMan 90', '더미휴먼 90', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.178061');
INSERT INTO public.member VALUES (92, '2026-10-23 14:01:50.185827', 'dummy91@example.com', 'dummyMan 91', '더미휴먼 91', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.185827');
INSERT INTO public.member VALUES (93, '2026-10-23 14:01:50.192964', 'dummy92@example.com', 'dummyMan 92', '더미휴먼 92', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.192964');
INSERT INTO public.member VALUES (94, '2026-10-23 14:01:50.200807', 'dummy93@example.com', 'dummyMan 93', '더미휴먼 93', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.200807');
INSERT INTO public.member VALUES (95, '2026-10-23 14:01:50.207595', 'dummy94@example.com', 'dummyMan 94', '더미휴먼 94', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.207595');
INSERT INTO public.member VALUES (96, '2026-10-23 14:01:50.21464', 'dummy95@example.com', 'dummyMan 95', '더미휴먼 95', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.21464');
INSERT INTO public.member VALUES (97, '2026-10-23 14:01:50.225464', 'dummy96@example.com', 'dummyMan 96', '더미휴먼 96', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.225464');
INSERT INTO public.member VALUES (98, '2026-10-23 14:01:50.234992', 'dummy97@example.com', 'dummyMan 97', '더미휴먼 97', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.234992');
INSERT INTO public.member VALUES (99, '2026-10-23 14:01:50.241914', 'dummy98@example.com', 'dummyMan 98', '더미휴먼 98', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.241914');
INSERT INTO public.member VALUES (100, '2026-10-23 14:01:50.248857', 'dummy99@example.com', 'dummyMan 99', '더미휴먼 99', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.248857');
INSERT INTO public.member VALUES (101, '2026-10-23 14:01:50.255826', 'dummy100@example.com', 'dummyMan 100', '더미휴먼 100', '1111', false, 'USER', 'ACTIVE', NULL, false, '2026-10-23 14:01:50.255826');


--
-- TOC entry 5066 (class 0 OID 19216)
-- Dependencies: 222
-- Data for Name: bike_maintenance; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.bike_maintenance VALUES (1, 'BREAK', '2026-10-23 14:02:10.428963', 'AVAILABLE', 1, 1);
INSERT INTO public.bike_maintenance VALUES (2, 'BREAK', '2026-10-23 14:02:10.501247', 'AVAILABLE', 2, 1);
INSERT INTO public.bike_maintenance VALUES (3, 'BREAK', '2026-10-23 14:02:10.510854', 'AVAILABLE', 3, 1);
INSERT INTO public.bike_maintenance VALUES (4, 'BREAK', '2026-10-23 14:02:10.515748', 'AVAILABLE', 4, 1);
INSERT INTO public.bike_maintenance VALUES (5, 'BREAK', '2026-10-23 14:02:10.524815', 'AVAILABLE', 5, 1);
INSERT INTO public.bike_maintenance VALUES (6, 'BREAK', '2026-10-23 14:02:10.532317', 'AVAILABLE', 6, 1);
INSERT INTO public.bike_maintenance VALUES (7, 'BREAK', '2026-10-23 14:02:10.541077', 'AVAILABLE', 7, 1);
INSERT INTO public.bike_maintenance VALUES (8, 'BREAK', '2026-10-23 14:02:10.551659', 'AVAILABLE', 8, 1);
INSERT INTO public.bike_maintenance VALUES (9, 'BREAK', '2026-10-23 14:02:10.560764', 'AVAILABLE', 9, 1);
INSERT INTO public.bike_maintenance VALUES (10, 'BREAK', '2026-10-23 14:02:10.569557', 'AVAILABLE', 10, 1);
INSERT INTO public.bike_maintenance VALUES (11, 'BREAK', '2026-10-23 14:02:10.57765', 'AVAILABLE', 11, 1);
INSERT INTO public.bike_maintenance VALUES (12, 'BREAK', '2026-10-23 14:02:10.593448', 'AVAILABLE', 12, 1);
INSERT INTO public.bike_maintenance VALUES (13, 'BREAK', '2026-10-23 14:02:10.602733', 'AVAILABLE', 13, 1);
INSERT INTO public.bike_maintenance VALUES (14, 'BREAK', '2026-10-23 14:02:10.611756', 'AVAILABLE', 14, 1);
INSERT INTO public.bike_maintenance VALUES (15, 'BREAK', '2026-10-23 14:02:10.619179', 'AVAILABLE', 15, 1);
INSERT INTO public.bike_maintenance VALUES (16, 'BREAK', '2026-10-23 14:02:10.625281', 'AVAILABLE', 16, 1);
INSERT INTO public.bike_maintenance VALUES (17, 'BREAK', '2026-10-23 14:02:10.630774', 'AVAILABLE', 17, 1);
INSERT INTO public.bike_maintenance VALUES (18, 'BREAK', '2026-10-23 14:02:10.63835', 'AVAILABLE', 18, 1);
INSERT INTO public.bike_maintenance VALUES (19, 'BREAK', '2026-10-23 14:02:10.645678', 'AVAILABLE', 19, 1);
INSERT INTO public.bike_maintenance VALUES (20, 'BREAK', '2026-10-23 14:02:10.653441', 'AVAILABLE', 20, 1);
INSERT INTO public.bike_maintenance VALUES (21, 'BREAK', '2026-10-23 14:02:10.660596', 'AVAILABLE', 21, 1);
INSERT INTO public.bike_maintenance VALUES (22, 'BREAK', '2026-10-23 14:02:10.668134', 'AVAILABLE', 22, 1);
INSERT INTO public.bike_maintenance VALUES (23, 'BREAK', '2026-10-23 14:02:10.675751', 'AVAILABLE', 23, 1);
INSERT INTO public.bike_maintenance VALUES (24, 'BREAK', '2026-10-23 14:02:10.683751', 'AVAILABLE', 24, 1);
INSERT INTO public.bike_maintenance VALUES (25, 'BREAK', '2026-10-23 14:02:10.691485', 'AVAILABLE', 25, 1);
INSERT INTO public.bike_maintenance VALUES (26, 'BREAK', '2026-10-23 14:02:10.699236', 'AVAILABLE', 26, 1);
INSERT INTO public.bike_maintenance VALUES (27, 'BREAK', '2026-10-23 14:02:10.707643', 'AVAILABLE', 27, 1);
INSERT INTO public.bike_maintenance VALUES (28, 'BREAK', '2026-10-23 14:02:10.715411', 'AVAILABLE', 28, 1);
INSERT INTO public.bike_maintenance VALUES (29, 'BREAK', '2026-10-23 14:02:10.723636', 'AVAILABLE', 29, 1);
INSERT INTO public.bike_maintenance VALUES (30, 'BREAK', '2026-10-23 14:02:10.730717', 'AVAILABLE', 30, 1);
INSERT INTO public.bike_maintenance VALUES (31, 'BREAK', '2026-10-23 14:02:10.738834', 'AVAILABLE', 31, 1);
INSERT INTO public.bike_maintenance VALUES (32, 'BREAK', '2026-10-23 14:02:10.746106', 'AVAILABLE', 32, 1);
INSERT INTO public.bike_maintenance VALUES (33, 'BREAK', '2026-10-23 14:02:10.753379', 'AVAILABLE', 33, 1);
INSERT INTO public.bike_maintenance VALUES (34, 'BREAK', '2026-10-23 14:02:10.759511', 'AVAILABLE', 34, 1);
INSERT INTO public.bike_maintenance VALUES (35, 'BREAK', '2026-10-23 14:02:10.766738', 'AVAILABLE', 35, 1);
INSERT INTO public.bike_maintenance VALUES (36, 'BREAK', '2026-10-23 14:02:10.775265', 'AVAILABLE', 36, 1);
INSERT INTO public.bike_maintenance VALUES (37, 'BREAK', '2026-10-23 14:02:10.781784', 'AVAILABLE', 37, 1);
INSERT INTO public.bike_maintenance VALUES (38, 'BREAK', '2026-10-23 14:02:10.790401', 'AVAILABLE', 38, 1);
INSERT INTO public.bike_maintenance VALUES (39, 'BREAK', '2026-10-23 14:02:10.797821', 'AVAILABLE', 39, 1);
INSERT INTO public.bike_maintenance VALUES (40, 'BREAK', '2026-10-23 14:02:10.802898', 'AVAILABLE', 40, 1);
INSERT INTO public.bike_maintenance VALUES (41, 'BREAK', '2026-10-23 14:02:10.80875', 'AVAILABLE', 41, 1);
INSERT INTO public.bike_maintenance VALUES (42, 'BREAK', '2026-10-23 14:02:10.814318', 'AVAILABLE', 42, 1);
INSERT INTO public.bike_maintenance VALUES (43, 'BREAK', '2026-10-23 14:02:10.821318', 'AVAILABLE', 43, 1);
INSERT INTO public.bike_maintenance VALUES (44, 'BREAK', '2026-10-23 14:02:10.828878', 'AVAILABLE', 44, 1);
INSERT INTO public.bike_maintenance VALUES (45, 'BREAK', '2026-10-23 14:02:10.835659', 'AVAILABLE', 45, 1);
INSERT INTO public.bike_maintenance VALUES (46, 'BREAK', '2026-10-23 14:02:10.843686', 'AVAILABLE', 46, 1);
INSERT INTO public.bike_maintenance VALUES (47, 'BREAK', '2026-10-23 14:02:10.85133', 'AVAILABLE', 47, 1);
INSERT INTO public.bike_maintenance VALUES (48, 'BREAK', '2026-10-23 14:02:10.860267', 'AVAILABLE', 48, 1);
INSERT INTO public.bike_maintenance VALUES (49, 'BREAK', '2026-10-23 14:02:10.867994', 'AVAILABLE', 49, 1);
INSERT INTO public.bike_maintenance VALUES (50, 'BREAK', '2026-10-23 14:02:10.875654', 'AVAILABLE', 50, 1);
INSERT INTO public.bike_maintenance VALUES (51, 'BREAK', '2026-10-23 14:02:10.883496', 'AVAILABLE', 51, 1);
INSERT INTO public.bike_maintenance VALUES (52, 'BREAK', '2026-10-23 14:02:10.89152', 'AVAILABLE', 52, 1);
INSERT INTO public.bike_maintenance VALUES (53, 'BREAK', '2026-10-23 14:02:10.899273', 'AVAILABLE', 53, 1);
INSERT INTO public.bike_maintenance VALUES (54, 'BREAK', '2026-10-23 14:02:10.907806', 'AVAILABLE', 54, 1);
INSERT INTO public.bike_maintenance VALUES (55, 'BREAK', '2026-10-23 14:02:10.914904', 'AVAILABLE', 55, 1);
INSERT INTO public.bike_maintenance VALUES (56, 'BREAK', '2026-10-23 14:02:10.923465', 'AVAILABLE', 56, 1);
INSERT INTO public.bike_maintenance VALUES (57, 'BREAK', '2026-10-23 14:02:10.931378', 'AVAILABLE', 57, 1);
INSERT INTO public.bike_maintenance VALUES (58, 'BREAK', '2026-10-23 14:02:10.939907', 'AVAILABLE', 58, 1);
INSERT INTO public.bike_maintenance VALUES (59, 'BREAK', '2026-10-23 14:02:10.948603', 'AVAILABLE', 59, 1);
INSERT INTO public.bike_maintenance VALUES (60, 'BREAK', '2026-10-23 14:02:10.956195', 'AVAILABLE', 60, 1);
INSERT INTO public.bike_maintenance VALUES (61, 'BREAK', '2026-10-23 14:02:10.963806', 'AVAILABLE', 61, 1);
INSERT INTO public.bike_maintenance VALUES (62, 'BREAK', '2026-10-23 14:02:10.972584', 'AVAILABLE', 62, 1);
INSERT INTO public.bike_maintenance VALUES (63, 'BREAK', '2026-10-23 14:02:10.979345', 'AVAILABLE', 63, 1);
INSERT INTO public.bike_maintenance VALUES (64, 'BREAK', '2026-10-23 14:02:10.986536', 'AVAILABLE', 64, 1);
INSERT INTO public.bike_maintenance VALUES (65, 'BREAK', '2026-10-23 14:02:10.9926', 'AVAILABLE', 65, 1);
INSERT INTO public.bike_maintenance VALUES (66, 'BREAK', '2026-10-23 14:02:10.997765', 'AVAILABLE', 66, 1);
INSERT INTO public.bike_maintenance VALUES (67, 'BREAK', '2026-10-23 14:02:11.004794', 'AVAILABLE', 67, 1);
INSERT INTO public.bike_maintenance VALUES (68, 'BREAK', '2026-10-23 14:02:11.011439', 'AVAILABLE', 68, 1);
INSERT INTO public.bike_maintenance VALUES (69, 'BREAK', '2026-10-23 14:02:11.018328', 'AVAILABLE', 69, 1);
INSERT INTO public.bike_maintenance VALUES (70, 'BREAK', '2026-10-23 14:02:11.02519', 'AVAILABLE', 70, 1);
INSERT INTO public.bike_maintenance VALUES (71, 'BREAK', '2026-10-23 14:02:11.031979', 'AVAILABLE', 71, 1);
INSERT INTO public.bike_maintenance VALUES (72, 'BREAK', '2026-10-23 14:02:11.040167', 'AVAILABLE', 72, 1);
INSERT INTO public.bike_maintenance VALUES (73, 'BREAK', '2026-10-23 14:02:11.046213', 'AVAILABLE', 73, 1);
INSERT INTO public.bike_maintenance VALUES (74, 'BREAK', '2026-10-23 14:02:11.053564', 'AVAILABLE', 74, 1);
INSERT INTO public.bike_maintenance VALUES (75, 'BREAK', '2026-10-23 14:02:11.060489', 'AVAILABLE', 75, 1);
INSERT INTO public.bike_maintenance VALUES (76, 'BREAK', '2026-10-23 14:02:11.066981', 'AVAILABLE', 76, 1);
INSERT INTO public.bike_maintenance VALUES (77, 'BREAK', '2026-10-23 14:02:11.075774', 'AVAILABLE', 77, 1);
INSERT INTO public.bike_maintenance VALUES (78, 'BREAK', '2026-10-23 14:02:11.08226', 'AVAILABLE', 78, 1);
INSERT INTO public.bike_maintenance VALUES (79, 'BREAK', '2026-10-23 14:02:11.091231', 'AVAILABLE', 79, 1);
INSERT INTO public.bike_maintenance VALUES (80, 'BREAK', '2026-10-23 14:02:11.097639', 'AVAILABLE', 80, 1);
INSERT INTO public.bike_maintenance VALUES (81, 'BREAK', '2026-10-23 14:02:11.105582', 'AVAILABLE', 81, 1);
INSERT INTO public.bike_maintenance VALUES (82, 'BREAK', '2026-10-23 14:02:11.111963', 'AVAILABLE', 82, 1);
INSERT INTO public.bike_maintenance VALUES (83, 'BREAK', '2026-10-23 14:02:11.118951', 'AVAILABLE', 83, 1);
INSERT INTO public.bike_maintenance VALUES (84, 'BREAK', '2026-10-23 14:02:11.126508', 'AVAILABLE', 84, 1);
INSERT INTO public.bike_maintenance VALUES (85, 'BREAK', '2026-10-23 14:02:11.13371', 'AVAILABLE', 85, 1);
INSERT INTO public.bike_maintenance VALUES (86, 'BREAK', '2026-10-23 14:02:11.141163', 'AVAILABLE', 86, 1);
INSERT INTO public.bike_maintenance VALUES (87, 'BREAK', '2026-10-23 14:02:11.148358', 'AVAILABLE', 87, 1);
INSERT INTO public.bike_maintenance VALUES (88, 'BREAK', '2026-10-23 14:02:11.155669', 'AVAILABLE', 88, 1);
INSERT INTO public.bike_maintenance VALUES (89, 'BREAK', '2026-10-23 14:02:11.16245', 'AVAILABLE', 89, 1);
INSERT INTO public.bike_maintenance VALUES (90, 'BREAK', '2026-10-23 14:02:11.166847', 'AVAILABLE', 90, 1);
INSERT INTO public.bike_maintenance VALUES (91, 'BREAK', '2026-10-23 14:02:11.17188', 'AVAILABLE', 91, 1);
INSERT INTO public.bike_maintenance VALUES (92, 'BREAK', '2026-10-23 14:02:11.178803', 'AVAILABLE', 92, 1);
INSERT INTO public.bike_maintenance VALUES (93, 'BREAK', '2026-10-23 14:02:11.187308', 'AVAILABLE', 93, 1);
INSERT INTO public.bike_maintenance VALUES (94, 'BREAK', '2026-10-23 14:02:11.194291', 'AVAILABLE', 94, 1);
INSERT INTO public.bike_maintenance VALUES (95, 'BREAK', '2026-10-23 14:02:11.200963', 'AVAILABLE', 95, 1);
INSERT INTO public.bike_maintenance VALUES (96, 'BREAK', '2026-10-23 14:02:11.208825', 'AVAILABLE', 96, 1);
INSERT INTO public.bike_maintenance VALUES (97, 'BREAK', '2026-10-23 14:02:11.218539', 'AVAILABLE', 97, 1);
INSERT INTO public.bike_maintenance VALUES (98, 'BREAK', '2026-10-23 14:02:11.225871', 'AVAILABLE', 98, 1);
INSERT INTO public.bike_maintenance VALUES (99, 'BREAK', '2026-10-23 14:02:11.234227', 'AVAILABLE', 99, 1);
INSERT INTO public.bike_maintenance VALUES (100, 'BREAK', '2026-10-23 14:02:11.241206', 'AVAILABLE', 100, 1);


--
-- TOC entry 5068 (class 0 OID 19232)
-- Dependencies: 224
-- Data for Name: datafiles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.datafiles VALUES (1, '더미 데이터파일 내용 1
입니다.', '2026-10-23 14:00:15.718756', '더미글 1', '2026-10-23 14:00:15.718756', 1);
INSERT INTO public.datafiles VALUES (2, '더미 데이터파일 내용 2
입니다.', '2026-10-23 14:00:15.788579', '더미글 2', '2026-10-23 14:00:15.788579', 1);
INSERT INTO public.datafiles VALUES (3, '더미 데이터파일 내용 3
입니다.', '2026-10-23 14:00:15.797006', '더미글 3', '2026-10-23 14:00:15.797006', 1);
INSERT INTO public.datafiles VALUES (4, '더미 데이터파일 내용 4
입니다.', '2026-10-23 14:00:15.804007', '더미글 4', '2026-10-23 14:00:15.804007', 1);
INSERT INTO public.datafiles VALUES (5, '더미 데이터파일 내용 5
입니다.', '2026-10-23 14:00:15.811527', '더미글 5', '2026-10-23 14:00:15.811527', 1);
INSERT INTO public.datafiles VALUES (6, '더미 데이터파일 내용 6
입니다.', '2026-10-23 14:00:15.820249', '더미글 6', '2026-10-23 14:00:15.820249', 1);
INSERT INTO public.datafiles VALUES (7, '더미 데이터파일 내용 7
입니다.', '2026-10-23 14:00:15.827765', '더미글 7', '2026-10-23 14:00:15.828804', 1);
INSERT INTO public.datafiles VALUES (8, '더미 데이터파일 내용 8
입니다.', '2026-10-23 14:00:15.836913', '더미글 8', '2026-10-23 14:00:15.836913', 1);
INSERT INTO public.datafiles VALUES (9, '더미 데이터파일 내용 9
입니다.', '2026-10-23 14:00:15.844961', '더미글 9', '2026-10-23 14:00:15.844961', 1);
INSERT INTO public.datafiles VALUES (10, '더미 데이터파일 내용 10
입니다.', '2026-10-23 14:00:15.85356', '더미글 10', '2026-10-23 14:00:15.85356', 1);
INSERT INTO public.datafiles VALUES (11, '더미 데이터파일 내용 11
입니다.', '2026-10-23 14:00:15.861142', '더미글 11', '2026-10-23 14:00:15.861142', 1);
INSERT INTO public.datafiles VALUES (12, '더미 데이터파일 내용 12
입니다.', '2026-10-23 14:00:15.869677', '더미글 12', '2026-10-23 14:00:15.869677', 1);
INSERT INTO public.datafiles VALUES (13, '더미 데이터파일 내용 13
입니다.', '2026-10-23 14:00:15.877642', '더미글 13', '2026-10-23 14:00:15.877642', 1);
INSERT INTO public.datafiles VALUES (14, '더미 데이터파일 내용 14
입니다.', '2026-10-23 14:00:15.886329', '더미글 14', '2026-10-23 14:00:15.886329', 1);
INSERT INTO public.datafiles VALUES (15, '더미 데이터파일 내용 15
입니다.', '2026-10-23 14:00:15.89391', '더미글 15', '2026-10-23 14:00:15.89391', 1);
INSERT INTO public.datafiles VALUES (16, '더미 데이터파일 내용 16
입니다.', '2026-10-23 14:00:15.902605', '더미글 16', '2026-10-23 14:00:15.902605', 1);
INSERT INTO public.datafiles VALUES (17, '더미 데이터파일 내용 17
입니다.', '2026-10-23 14:00:15.909612', '더미글 17', '2026-10-23 14:00:15.911125', 1);
INSERT INTO public.datafiles VALUES (18, '더미 데이터파일 내용 18
입니다.', '2026-10-23 14:00:15.918041', '더미글 18', '2026-10-23 14:00:15.918041', 1);
INSERT INTO public.datafiles VALUES (19, '더미 데이터파일 내용 19
입니다.', '2026-10-23 14:00:15.923567', '더미글 19', '2026-10-23 14:00:15.923567', 1);
INSERT INTO public.datafiles VALUES (20, '더미 데이터파일 내용 20
입니다.', '2026-10-23 14:00:15.929575', '더미글 20', '2026-10-23 14:00:15.929575', 1);
INSERT INTO public.datafiles VALUES (21, '더미 데이터파일 내용 21
입니다.', '2026-10-23 14:00:15.936023', '더미글 21', '2026-10-23 14:00:15.936023', 1);
INSERT INTO public.datafiles VALUES (22, '더미 데이터파일 내용 22
입니다.', '2026-10-23 14:00:15.941018', '더미글 22', '2026-10-23 14:00:15.941018', 1);
INSERT INTO public.datafiles VALUES (23, '더미 데이터파일 내용 23
입니다.', '2026-10-23 14:00:15.949996', '더미글 23', '2026-10-23 14:00:15.949996', 1);
INSERT INTO public.datafiles VALUES (24, '더미 데이터파일 내용 24
입니다.', '2026-10-23 14:00:15.957036', '더미글 24', '2026-10-23 14:00:15.957036', 1);
INSERT INTO public.datafiles VALUES (25, '더미 데이터파일 내용 25
입니다.', '2026-10-23 14:00:15.96501', '더미글 25', '2026-10-23 14:00:15.96501', 1);
INSERT INTO public.datafiles VALUES (26, '더미 데이터파일 내용 26
입니다.', '2026-10-23 14:00:15.973008', '더미글 26', '2026-10-23 14:00:15.973008', 1);
INSERT INTO public.datafiles VALUES (27, '더미 데이터파일 내용 27
입니다.', '2026-10-23 14:00:15.980757', '더미글 27', '2026-10-23 14:00:15.980757', 1);
INSERT INTO public.datafiles VALUES (28, '더미 데이터파일 내용 28
입니다.', '2026-10-23 14:00:15.98884', '더미글 28', '2026-10-23 14:00:15.98884', 1);
INSERT INTO public.datafiles VALUES (29, '더미 데이터파일 내용 29
입니다.', '2026-10-23 14:00:15.997011', '더미글 29', '2026-10-23 14:00:15.997011', 1);
INSERT INTO public.datafiles VALUES (30, '더미 데이터파일 내용 30
입니다.', '2026-10-23 14:00:16.005527', '더미글 30', '2026-10-23 14:00:16.005527', 1);
INSERT INTO public.datafiles VALUES (31, '더미 데이터파일 내용 31
입니다.', '2026-10-23 14:00:16.012533', '더미글 31', '2026-10-23 14:00:16.012533', 1);
INSERT INTO public.datafiles VALUES (32, '더미 데이터파일 내용 32
입니다.', '2026-10-23 14:00:16.021343', '더미글 32', '2026-10-23 14:00:16.021343', 1);
INSERT INTO public.datafiles VALUES (33, '더미 데이터파일 내용 33
입니다.', '2026-10-23 14:00:16.029247', '더미글 33', '2026-10-23 14:00:16.029247', 1);
INSERT INTO public.datafiles VALUES (34, '더미 데이터파일 내용 34
입니다.', '2026-10-23 14:00:16.036847', '더미글 34', '2026-10-23 14:00:16.036847', 1);
INSERT INTO public.datafiles VALUES (35, '더미 데이터파일 내용 35
입니다.', '2026-10-23 14:00:16.044849', '더미글 35', '2026-10-23 14:00:16.044849', 1);
INSERT INTO public.datafiles VALUES (36, '더미 데이터파일 내용 36
입니다.', '2026-10-23 14:00:16.052941', '더미글 36', '2026-10-23 14:00:16.052941', 1);
INSERT INTO public.datafiles VALUES (37, '더미 데이터파일 내용 37
입니다.', '2026-10-23 14:00:16.063502', '더미글 37', '2026-10-23 14:00:16.063502', 1);
INSERT INTO public.datafiles VALUES (38, '더미 데이터파일 내용 38
입니다.', '2026-10-23 14:00:16.07011', '더미글 38', '2026-10-23 14:00:16.07011', 1);
INSERT INTO public.datafiles VALUES (39, '더미 데이터파일 내용 39
입니다.', '2026-10-23 14:00:16.076142', '더미글 39', '2026-10-23 14:00:16.076142', 1);
INSERT INTO public.datafiles VALUES (40, '더미 데이터파일 내용 40
입니다.', '2026-10-23 14:00:16.083631', '더미글 40', '2026-10-23 14:00:16.083631', 1);
INSERT INTO public.datafiles VALUES (41, '더미 데이터파일 내용 41
입니다.', '2026-10-23 14:00:16.090214', '더미글 41', '2026-10-23 14:00:16.090214', 1);
INSERT INTO public.datafiles VALUES (42, '더미 데이터파일 내용 42
입니다.', '2026-10-23 14:00:16.095549', '더미글 42', '2026-10-23 14:00:16.095549', 1);
INSERT INTO public.datafiles VALUES (43, '더미 데이터파일 내용 43
입니다.', '2026-10-23 14:00:16.102109', '더미글 43', '2026-10-23 14:00:16.102109', 1);
INSERT INTO public.datafiles VALUES (44, '더미 데이터파일 내용 44
입니다.', '2026-10-23 14:00:16.107137', '더미글 44', '2026-10-23 14:00:16.107137', 1);
INSERT INTO public.datafiles VALUES (45, '더미 데이터파일 내용 45
입니다.', '2026-10-23 14:00:16.112375', '더미글 45', '2026-10-23 14:00:16.112375', 1);
INSERT INTO public.datafiles VALUES (46, '더미 데이터파일 내용 46
입니다.', '2026-10-23 14:00:16.120008', '더미글 46', '2026-10-23 14:00:16.120008', 1);
INSERT INTO public.datafiles VALUES (47, '더미 데이터파일 내용 47
입니다.', '2026-10-23 14:00:16.127158', '더미글 47', '2026-10-23 14:00:16.127158', 1);
INSERT INTO public.datafiles VALUES (48, '더미 데이터파일 내용 48
입니다.', '2026-10-23 14:00:16.134376', '더미글 48', '2026-10-23 14:00:16.134376', 1);
INSERT INTO public.datafiles VALUES (49, '더미 데이터파일 내용 49
입니다.', '2026-10-23 14:00:16.141014', '더미글 49', '2026-10-23 14:00:16.141014', 1);
INSERT INTO public.datafiles VALUES (50, '더미 데이터파일 내용 50
입니다.', '2026-10-23 14:00:16.152874', '더미글 50', '2026-10-23 14:00:16.152874', 1);
INSERT INTO public.datafiles VALUES (51, '더미 데이터파일 내용 51
입니다.', '2026-10-23 14:00:16.159789', '더미글 51', '2026-10-23 14:00:16.159789', 1);
INSERT INTO public.datafiles VALUES (52, '더미 데이터파일 내용 52
입니다.', '2026-10-23 14:00:16.168324', '더미글 52', '2026-10-23 14:00:16.168324', 1);
INSERT INTO public.datafiles VALUES (53, '더미 데이터파일 내용 53
입니다.', '2026-10-23 14:00:16.175068', '더미글 53', '2026-10-23 14:00:16.175068', 1);
INSERT INTO public.datafiles VALUES (54, '더미 데이터파일 내용 54
입니다.', '2026-10-23 14:00:16.181721', '더미글 54', '2026-10-23 14:00:16.181721', 1);
INSERT INTO public.datafiles VALUES (55, '더미 데이터파일 내용 55
입니다.', '2026-10-23 14:00:16.189207', '더미글 55', '2026-10-23 14:00:16.189207', 1);
INSERT INTO public.datafiles VALUES (56, '더미 데이터파일 내용 56
입니다.', '2026-10-23 14:00:16.19695', '더미글 56', '2026-10-23 14:00:16.19695', 1);
INSERT INTO public.datafiles VALUES (57, '더미 데이터파일 내용 57
입니다.', '2026-10-23 14:00:16.204153', '더미글 57', '2026-10-23 14:00:16.204153', 1);
INSERT INTO public.datafiles VALUES (58, '더미 데이터파일 내용 58
입니다.', '2026-10-23 14:00:16.211354', '더미글 58', '2026-10-23 14:00:16.211354', 1);
INSERT INTO public.datafiles VALUES (59, '더미 데이터파일 내용 59
입니다.', '2026-10-23 14:00:16.219885', '더미글 59', '2026-10-23 14:00:16.219885', 1);
INSERT INTO public.datafiles VALUES (60, '더미 데이터파일 내용 60
입니다.', '2026-10-23 14:00:16.227247', '더미글 60', '2026-10-23 14:00:16.227247', 1);
INSERT INTO public.datafiles VALUES (61, '더미 데이터파일 내용 61
입니다.', '2026-10-23 14:00:16.237295', '더미글 61', '2026-10-23 14:00:16.237295', 1);
INSERT INTO public.datafiles VALUES (62, '더미 데이터파일 내용 62
입니다.', '2026-10-23 14:00:16.243408', '더미글 62', '2026-10-23 14:00:16.244421', 1);
INSERT INTO public.datafiles VALUES (63, '더미 데이터파일 내용 63
입니다.', '2026-10-23 14:00:16.252192', '더미글 63', '2026-10-23 14:00:16.252192', 1);
INSERT INTO public.datafiles VALUES (64, '더미 데이터파일 내용 64
입니다.', '2026-10-23 14:00:16.259446', '더미글 64', '2026-10-23 14:00:16.259446', 1);
INSERT INTO public.datafiles VALUES (65, '더미 데이터파일 내용 65
입니다.', '2026-10-23 14:00:16.269269', '더미글 65', '2026-10-23 14:00:16.269269', 1);
INSERT INTO public.datafiles VALUES (66, '더미 데이터파일 내용 66
입니다.', '2026-10-23 14:00:16.276183', '더미글 66', '2026-10-23 14:00:16.276183', 1);
INSERT INTO public.datafiles VALUES (67, '더미 데이터파일 내용 67
입니다.', '2026-10-23 14:00:16.285438', '더미글 67', '2026-10-23 14:00:16.285438', 1);
INSERT INTO public.datafiles VALUES (68, '더미 데이터파일 내용 68
입니다.', '2026-10-23 14:00:16.292449', '더미글 68', '2026-10-23 14:00:16.292449', 1);
INSERT INTO public.datafiles VALUES (69, '더미 데이터파일 내용 69
입니다.', '2026-10-23 14:00:16.301019', '더미글 69', '2026-10-23 14:00:16.301019', 1);
INSERT INTO public.datafiles VALUES (70, '더미 데이터파일 내용 70
입니다.', '2026-10-23 14:00:16.306029', '더미글 70', '2026-10-23 14:00:16.306029', 1);
INSERT INTO public.datafiles VALUES (71, '더미 데이터파일 내용 71
입니다.', '2026-10-23 14:00:16.31293', '더미글 71', '2026-10-23 14:00:16.31293', 1);
INSERT INTO public.datafiles VALUES (72, '더미 데이터파일 내용 72
입니다.', '2026-10-23 14:00:16.320503', '더미글 72', '2026-10-23 14:00:16.320503', 1);
INSERT INTO public.datafiles VALUES (73, '더미 데이터파일 내용 73
입니다.', '2026-10-23 14:00:16.327077', '더미글 73', '2026-10-23 14:00:16.327077', 1);
INSERT INTO public.datafiles VALUES (74, '더미 데이터파일 내용 74
입니다.', '2026-10-23 14:00:16.339129', '더미글 74', '2026-10-23 14:00:16.339129', 1);
INSERT INTO public.datafiles VALUES (75, '더미 데이터파일 내용 75
입니다.', '2026-10-23 14:00:16.345707', '더미글 75', '2026-10-23 14:00:16.345707', 1);
INSERT INTO public.datafiles VALUES (76, '더미 데이터파일 내용 76
입니다.', '2026-10-23 14:00:16.353107', '더미글 76', '2026-10-23 14:00:16.353107', 1);
INSERT INTO public.datafiles VALUES (77, '더미 데이터파일 내용 77
입니다.', '2026-10-23 14:00:16.361189', '더미글 77', '2026-10-23 14:00:16.361189', 1);
INSERT INTO public.datafiles VALUES (78, '더미 데이터파일 내용 78
입니다.', '2026-10-23 14:00:16.370137', '더미글 78', '2026-10-23 14:00:16.370137', 1);
INSERT INTO public.datafiles VALUES (79, '더미 데이터파일 내용 79
입니다.', '2026-10-23 14:00:16.377137', '더미글 79', '2026-10-23 14:00:16.377137', 1);
INSERT INTO public.datafiles VALUES (80, '더미 데이터파일 내용 80
입니다.', '2026-10-23 14:00:16.386641', '더미글 80', '2026-10-23 14:00:16.386641', 1);
INSERT INTO public.datafiles VALUES (81, '더미 데이터파일 내용 81
입니다.', '2026-10-23 14:00:16.395611', '더미글 81', '2026-10-23 14:00:16.395611', 1);
INSERT INTO public.datafiles VALUES (82, '더미 데이터파일 내용 82
입니다.', '2026-10-23 14:00:16.405313', '더미글 82', '2026-10-23 14:00:16.405313', 1);
INSERT INTO public.datafiles VALUES (83, '더미 데이터파일 내용 83
입니다.', '2026-10-23 14:00:16.412391', '더미글 83', '2026-10-23 14:00:16.412391', 1);
INSERT INTO public.datafiles VALUES (84, '더미 데이터파일 내용 84
입니다.', '2026-10-23 14:00:16.420961', '더미글 84', '2026-10-23 14:00:16.420961', 1);
INSERT INTO public.datafiles VALUES (85, '더미 데이터파일 내용 85
입니다.', '2026-10-23 14:00:16.427971', '더미글 85', '2026-10-23 14:00:16.427971', 1);
INSERT INTO public.datafiles VALUES (86, '더미 데이터파일 내용 86
입니다.', '2026-10-23 14:00:16.436602', '더미글 86', '2026-10-23 14:00:16.436602', 1);
INSERT INTO public.datafiles VALUES (87, '더미 데이터파일 내용 87
입니다.', '2026-10-23 14:00:16.443214', '더미글 87', '2026-10-23 14:00:16.444182', 1);
INSERT INTO public.datafiles VALUES (88, '더미 데이터파일 내용 88
입니다.', '2026-10-23 14:00:16.452761', '더미글 88', '2026-10-23 14:00:16.452761', 1);
INSERT INTO public.datafiles VALUES (89, '더미 데이터파일 내용 89
입니다.', '2026-10-23 14:00:16.465894', '더미글 89', '2026-10-23 14:00:16.465894', 1);
INSERT INTO public.datafiles VALUES (90, '더미 데이터파일 내용 90
입니다.', '2026-10-23 14:00:16.47247', '더미글 90', '2026-10-23 14:00:16.47247', 1);
INSERT INTO public.datafiles VALUES (91, '더미 데이터파일 내용 91
입니다.', '2026-10-23 14:00:16.48014', '더미글 91', '2026-10-23 14:00:16.48014', 1);
INSERT INTO public.datafiles VALUES (92, '더미 데이터파일 내용 92
입니다.', '2026-10-23 14:00:16.489591', '더미글 92', '2026-10-23 14:00:16.489591', 1);
INSERT INTO public.datafiles VALUES (93, '더미 데이터파일 내용 93
입니다.', '2026-10-23 14:00:16.497288', '더미글 93', '2026-10-23 14:00:16.497288', 1);
INSERT INTO public.datafiles VALUES (94, '더미 데이터파일 내용 94
입니다.', '2026-10-23 14:00:16.504828', '더미글 94', '2026-10-23 14:00:16.504828', 1);
INSERT INTO public.datafiles VALUES (95, '더미 데이터파일 내용 95
입니다.', '2026-10-23 14:00:16.50783', '더미글 95', '2026-10-23 14:00:16.50783', 1);
INSERT INTO public.datafiles VALUES (96, '더미 데이터파일 내용 96
입니다.', '2026-10-23 14:00:16.514333', '더미글 96', '2026-10-23 14:00:16.514333', 1);
INSERT INTO public.datafiles VALUES (97, '더미 데이터파일 내용 97
입니다.', '2026-10-23 14:00:16.519573', '더미글 97', '2026-10-23 14:00:16.519573', 1);
INSERT INTO public.datafiles VALUES (98, '더미 데이터파일 내용 98
입니다.', '2026-10-23 14:00:16.525086', '더미글 98', '2026-10-23 14:00:16.525086', 1);
INSERT INTO public.datafiles VALUES (99, '더미 데이터파일 내용 99
입니다.', '2026-10-23 14:00:16.531997', '더미글 99', '2026-10-23 14:00:16.531997', 1);
INSERT INTO public.datafiles VALUES (100, '더미 데이터파일 내용 100
입니다.', '2026-10-23 14:00:16.539105', '더미글 100', '2026-10-23 14:00:16.539105', 1);


--
-- TOC entry 5070 (class 0 OID 19245)
-- Dependencies: 226
-- Data for Name: facility; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.facility VALUES (1, '2026-10-23 14:00:58.70936', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.70936');
INSERT INTO public.facility VALUES (2, '2026-10-23 14:00:58.79734', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.79734');
INSERT INTO public.facility VALUES (3, '2026-10-23 14:00:58.807291', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.807291');
INSERT INTO public.facility VALUES (4, '2026-10-23 14:00:58.814444', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.814444');
INSERT INTO public.facility VALUES (5, '2026-10-23 14:00:58.822998', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.822998');
INSERT INTO public.facility VALUES (6, '2026-10-23 14:00:58.831738', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.831738');
INSERT INTO public.facility VALUES (7, '2026-10-23 14:00:58.839182', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.839182');
INSERT INTO public.facility VALUES (8, '2026-10-23 14:00:58.847277', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.847277');
INSERT INTO public.facility VALUES (9, '2026-10-23 14:00:58.854492', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.854492');
INSERT INTO public.facility VALUES (10, '2026-10-23 14:00:58.862891', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.862891');
INSERT INTO public.facility VALUES (11, '2026-10-23 14:00:58.869787', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.869787');
INSERT INTO public.facility VALUES (12, '2026-10-23 14:00:58.87813', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.87813');
INSERT INTO public.facility VALUES (13, '2026-10-23 14:00:58.885329', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.885329');
INSERT INTO public.facility VALUES (14, '2026-10-23 14:00:58.891402', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.891402');
INSERT INTO public.facility VALUES (15, '2026-10-23 14:00:58.897468', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.897468');
INSERT INTO public.facility VALUES (16, '2026-10-23 14:00:58.903191', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.903191');
INSERT INTO public.facility VALUES (17, '2026-10-23 14:00:58.911053', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.911053');
INSERT INTO public.facility VALUES (18, '2026-10-23 14:00:58.918085', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.918085');
INSERT INTO public.facility VALUES (19, '2026-10-23 14:00:58.925282', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.925282');
INSERT INTO public.facility VALUES (20, '2026-10-23 14:00:58.932403', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.932403');
INSERT INTO public.facility VALUES (21, '2026-10-23 14:00:58.939661', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.939661');
INSERT INTO public.facility VALUES (22, '2026-10-23 14:00:58.94819', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.94819');
INSERT INTO public.facility VALUES (23, '2026-10-23 14:00:58.954769', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.954769');
INSERT INTO public.facility VALUES (24, '2026-10-23 14:00:58.963804', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.963804');
INSERT INTO public.facility VALUES (25, '2026-10-23 14:00:58.971014', 'AVAILABLE', 'ELEVATOR', 'CITY_HALL', '2026-10-23 14:00:58.971014');
INSERT INTO public.facility VALUES (26, '2026-10-23 14:00:58.979621', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:58.979621');
INSERT INTO public.facility VALUES (27, '2026-10-23 14:00:58.986909', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:58.986909');
INSERT INTO public.facility VALUES (28, '2026-10-23 14:00:58.994621', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:58.994621');
INSERT INTO public.facility VALUES (29, '2026-10-23 14:00:59.001678', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.001678');
INSERT INTO public.facility VALUES (30, '2026-10-23 14:00:59.008194', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.00916');
INSERT INTO public.facility VALUES (31, '2026-10-23 14:00:59.013895', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.013895');
INSERT INTO public.facility VALUES (32, '2026-10-23 14:00:59.02085', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.02085');
INSERT INTO public.facility VALUES (33, '2026-10-23 14:00:59.029134', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.029134');
INSERT INTO public.facility VALUES (34, '2026-10-23 14:00:59.03622', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.03622');
INSERT INTO public.facility VALUES (35, '2026-10-23 14:00:59.043351', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.043351');
INSERT INTO public.facility VALUES (36, '2026-10-23 14:00:59.050372', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.050372');
INSERT INTO public.facility VALUES (37, '2026-10-23 14:00:59.057087', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.057087');
INSERT INTO public.facility VALUES (38, '2026-10-23 14:00:59.064641', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.064641');
INSERT INTO public.facility VALUES (39, '2026-10-23 14:00:59.07174', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.07174');
INSERT INTO public.facility VALUES (40, '2026-10-23 14:00:59.080255', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.080255');
INSERT INTO public.facility VALUES (41, '2026-10-23 14:00:59.086931', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.086931');
INSERT INTO public.facility VALUES (42, '2026-10-23 14:00:59.096164', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.096164');
INSERT INTO public.facility VALUES (43, '2026-10-23 14:00:59.102823', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.102823');
INSERT INTO public.facility VALUES (44, '2026-10-23 14:00:59.111659', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.111659');
INSERT INTO public.facility VALUES (45, '2026-10-23 14:00:59.118342', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.118342');
INSERT INTO public.facility VALUES (46, '2026-10-23 14:00:59.124627', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.124627');
INSERT INTO public.facility VALUES (47, '2026-10-23 14:00:59.132839', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.132839');
INSERT INTO public.facility VALUES (48, '2026-10-23 14:00:59.140246', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.140246');
INSERT INTO public.facility VALUES (49, '2026-10-23 14:00:59.148194', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.148194');
INSERT INTO public.facility VALUES (50, '2026-10-23 14:00:59.154355', 'AVAILABLE', 'BIKE_STATION', 'CITY_HALL', '2026-10-23 14:00:59.154355');
INSERT INTO public.facility VALUES (51, '2026-10-23 14:00:59.163566', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.163566');
INSERT INTO public.facility VALUES (52, '2026-10-23 14:00:59.168724', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.168724');
INSERT INTO public.facility VALUES (53, '2026-10-23 14:00:59.174615', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.174615');
INSERT INTO public.facility VALUES (54, '2026-10-23 14:00:59.181791', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.181791');
INSERT INTO public.facility VALUES (55, '2026-10-23 14:00:59.189078', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.189078');
INSERT INTO public.facility VALUES (56, '2026-10-23 14:00:59.19694', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.19694');
INSERT INTO public.facility VALUES (57, '2026-10-23 14:00:59.203288', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.203288');
INSERT INTO public.facility VALUES (58, '2026-10-23 14:00:59.211203', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.211203');
INSERT INTO public.facility VALUES (59, '2026-10-23 14:00:59.21822', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.21822');
INSERT INTO public.facility VALUES (60, '2026-10-23 14:00:59.224937', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.224937');
INSERT INTO public.facility VALUES (61, '2026-10-23 14:00:59.232002', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.232002');
INSERT INTO public.facility VALUES (62, '2026-10-23 14:00:59.239151', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.239695');
INSERT INTO public.facility VALUES (63, '2026-10-23 14:00:59.24687', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.24687');
INSERT INTO public.facility VALUES (64, '2026-10-23 14:00:59.253228', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.253228');
INSERT INTO public.facility VALUES (65, '2026-10-23 14:00:59.262451', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.262451');
INSERT INTO public.facility VALUES (66, '2026-10-23 14:00:59.26991', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.26991');
INSERT INTO public.facility VALUES (67, '2026-10-23 14:00:59.278137', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.278137');
INSERT INTO public.facility VALUES (68, '2026-10-23 14:00:59.285698', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.285698');
INSERT INTO public.facility VALUES (69, '2026-10-23 14:00:59.292616', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.292616');
INSERT INTO public.facility VALUES (70, '2026-10-23 14:00:59.299939', 'AVAILABLE', 'SCREEN_DOOR', 'CITY_HALL', '2026-10-23 14:00:59.299939');
INSERT INTO public.facility VALUES (71, '2026-10-23 14:00:59.307171', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.307171');
INSERT INTO public.facility VALUES (72, '2026-10-23 14:00:59.315032', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.315032');
INSERT INTO public.facility VALUES (73, '2026-10-23 14:00:59.322291', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.322291');
INSERT INTO public.facility VALUES (74, '2026-10-23 14:00:59.331247', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.331247');
INSERT INTO public.facility VALUES (75, '2026-10-23 14:00:59.340627', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.340627');
INSERT INTO public.facility VALUES (76, '2026-10-23 14:00:59.349091', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.349091');
INSERT INTO public.facility VALUES (77, '2026-10-23 14:00:59.359557', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.359557');
INSERT INTO public.facility VALUES (78, '2026-10-23 14:00:59.370132', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.370132');
INSERT INTO public.facility VALUES (79, '2026-10-23 14:00:59.38544', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.38544');
INSERT INTO public.facility VALUES (80, '2026-10-23 14:00:59.398477', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.398477');
INSERT INTO public.facility VALUES (81, '2026-10-23 14:00:59.409689', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.409689');
INSERT INTO public.facility VALUES (82, '2026-10-23 14:00:59.420229', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.420229');
INSERT INTO public.facility VALUES (83, '2026-10-23 14:00:59.430607', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.430607');
INSERT INTO public.facility VALUES (84, '2026-10-23 14:00:59.441655', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.441655');
INSERT INTO public.facility VALUES (85, '2026-10-23 14:00:59.450837', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.451395');
INSERT INTO public.facility VALUES (86, '2026-10-23 14:00:59.460941', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.460941');
INSERT INTO public.facility VALUES (87, '2026-10-23 14:00:59.471011', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.471011');
INSERT INTO public.facility VALUES (88, '2026-10-23 14:00:59.480028', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.480028');
INSERT INTO public.facility VALUES (89, '2026-10-23 14:00:59.486281', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.486281');
INSERT INTO public.facility VALUES (90, '2026-10-23 14:00:59.492134', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.492134');
INSERT INTO public.facility VALUES (91, '2026-10-23 14:00:59.499556', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.499556');
INSERT INTO public.facility VALUES (92, '2026-10-23 14:00:59.506812', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.507148');
INSERT INTO public.facility VALUES (93, '2026-10-23 14:00:59.513861', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.513861');
INSERT INTO public.facility VALUES (94, '2026-10-23 14:00:59.529774', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.529774');
INSERT INTO public.facility VALUES (95, '2026-10-23 14:00:59.536845', 'AVAILABLE', 'BIKE_RACK', 'CITY_HALL', '2026-10-23 14:00:59.536845');


--
-- TOC entry 5072 (class 0 OID 19259)
-- Dependencies: 228
-- Data for Name: facility_maintenance; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.facility_maintenance VALUES (1, '2026-10-23 14:02:34.905557', 'OPERATION_FAILURE', 'AVAILABLE', 1, 1);
INSERT INTO public.facility_maintenance VALUES (2, '2026-10-23 14:02:34.999747', 'OPERATION_FAILURE', 'AVAILABLE', 2, 1);
INSERT INTO public.facility_maintenance VALUES (3, '2026-10-23 14:02:35.009741', 'OPERATION_FAILURE', 'AVAILABLE', 3, 1);
INSERT INTO public.facility_maintenance VALUES (4, '2026-10-23 14:02:35.015739', 'OPERATION_FAILURE', 'AVAILABLE', 4, 1);
INSERT INTO public.facility_maintenance VALUES (5, '2026-10-23 14:02:35.025742', 'OPERATION_FAILURE', 'AVAILABLE', 5, 1);
INSERT INTO public.facility_maintenance VALUES (6, '2026-10-23 14:02:35.035242', 'OPERATION_FAILURE', 'AVAILABLE', 6, 1);
INSERT INTO public.facility_maintenance VALUES (7, '2026-10-23 14:02:35.046219', 'OPERATION_FAILURE', 'AVAILABLE', 7, 1);
INSERT INTO public.facility_maintenance VALUES (8, '2026-10-23 14:02:35.055942', 'OPERATION_FAILURE', 'AVAILABLE', 8, 1);
INSERT INTO public.facility_maintenance VALUES (9, '2026-10-23 14:02:35.064379', 'OPERATION_FAILURE', 'AVAILABLE', 9, 1);
INSERT INTO public.facility_maintenance VALUES (10, '2026-10-23 14:02:35.073246', 'OPERATION_FAILURE', 'AVAILABLE', 10, 1);
INSERT INTO public.facility_maintenance VALUES (11, '2026-10-23 14:02:35.082203', 'OPERATION_FAILURE', 'AVAILABLE', 11, 1);
INSERT INTO public.facility_maintenance VALUES (12, '2026-10-23 14:02:35.093327', 'OPERATION_FAILURE', 'AVAILABLE', 12, 1);
INSERT INTO public.facility_maintenance VALUES (13, '2026-10-23 14:02:35.102336', 'OPERATION_FAILURE', 'AVAILABLE', 13, 1);
INSERT INTO public.facility_maintenance VALUES (14, '2026-10-23 14:02:35.110809', 'OPERATION_FAILURE', 'AVAILABLE', 14, 1);
INSERT INTO public.facility_maintenance VALUES (15, '2026-10-23 14:02:35.118089', 'OPERATION_FAILURE', 'AVAILABLE', 15, 1);
INSERT INTO public.facility_maintenance VALUES (16, '2026-10-23 14:02:35.128273', 'OPERATION_FAILURE', 'AVAILABLE', 16, 1);
INSERT INTO public.facility_maintenance VALUES (17, '2026-10-23 14:02:35.137685', 'OPERATION_FAILURE', 'AVAILABLE', 17, 1);
INSERT INTO public.facility_maintenance VALUES (18, '2026-10-23 14:02:35.14676', 'OPERATION_FAILURE', 'AVAILABLE', 18, 1);
INSERT INTO public.facility_maintenance VALUES (19, '2026-10-23 14:02:35.155827', 'OPERATION_FAILURE', 'AVAILABLE', 19, 1);
INSERT INTO public.facility_maintenance VALUES (20, '2026-10-23 14:02:35.163137', 'OPERATION_FAILURE', 'AVAILABLE', 20, 1);
INSERT INTO public.facility_maintenance VALUES (21, '2026-10-23 14:02:35.173841', 'OPERATION_FAILURE', 'AVAILABLE', 21, 1);
INSERT INTO public.facility_maintenance VALUES (22, '2026-10-23 14:02:35.18084', 'OPERATION_FAILURE', 'AVAILABLE', 22, 1);
INSERT INTO public.facility_maintenance VALUES (23, '2026-10-23 14:02:35.186879', 'OPERATION_FAILURE', 'AVAILABLE', 23, 1);
INSERT INTO public.facility_maintenance VALUES (24, '2026-10-23 14:02:35.196015', 'OPERATION_FAILURE', 'AVAILABLE', 24, 1);
INSERT INTO public.facility_maintenance VALUES (25, '2026-10-23 14:02:35.204862', 'OPERATION_FAILURE', 'AVAILABLE', 25, 1);
INSERT INTO public.facility_maintenance VALUES (26, '2026-10-23 14:02:35.214161', 'OPERATION_FAILURE', 'AVAILABLE', 26, 1);
INSERT INTO public.facility_maintenance VALUES (27, '2026-10-23 14:02:35.220816', 'OPERATION_FAILURE', 'AVAILABLE', 27, 1);
INSERT INTO public.facility_maintenance VALUES (28, '2026-10-23 14:02:35.227009', 'OPERATION_FAILURE', 'AVAILABLE', 28, 1);
INSERT INTO public.facility_maintenance VALUES (29, '2026-10-23 14:02:35.236647', 'OPERATION_FAILURE', 'AVAILABLE', 29, 1);
INSERT INTO public.facility_maintenance VALUES (30, '2026-10-23 14:02:35.246243', 'OPERATION_FAILURE', 'AVAILABLE', 30, 1);
INSERT INTO public.facility_maintenance VALUES (31, '2026-10-23 14:02:35.256891', 'OPERATION_FAILURE', 'AVAILABLE', 31, 1);
INSERT INTO public.facility_maintenance VALUES (32, '2026-10-23 14:02:35.267076', 'OPERATION_FAILURE', 'AVAILABLE', 32, 1);
INSERT INTO public.facility_maintenance VALUES (33, '2026-10-23 14:02:35.277756', 'OPERATION_FAILURE', 'AVAILABLE', 33, 1);
INSERT INTO public.facility_maintenance VALUES (34, '2026-10-23 14:02:35.281771', 'OPERATION_FAILURE', 'AVAILABLE', 34, 1);
INSERT INTO public.facility_maintenance VALUES (35, '2026-10-23 14:02:35.290418', 'OPERATION_FAILURE', 'AVAILABLE', 35, 1);
INSERT INTO public.facility_maintenance VALUES (36, '2026-10-23 14:02:35.297703', 'OPERATION_FAILURE', 'AVAILABLE', 36, 1);
INSERT INTO public.facility_maintenance VALUES (37, '2026-10-23 14:02:35.301959', 'OPERATION_FAILURE', 'AVAILABLE', 37, 1);
INSERT INTO public.facility_maintenance VALUES (38, '2026-10-23 14:02:35.308062', 'OPERATION_FAILURE', 'AVAILABLE', 38, 1);
INSERT INTO public.facility_maintenance VALUES (39, '2026-10-23 14:02:35.314417', 'OPERATION_FAILURE', 'AVAILABLE', 39, 1);
INSERT INTO public.facility_maintenance VALUES (40, '2026-10-23 14:02:35.318509', 'OPERATION_FAILURE', 'AVAILABLE', 40, 1);
INSERT INTO public.facility_maintenance VALUES (41, '2026-10-23 14:02:35.328159', 'OPERATION_FAILURE', 'AVAILABLE', 41, 1);
INSERT INTO public.facility_maintenance VALUES (42, '2026-10-23 14:02:35.335506', 'OPERATION_FAILURE', 'AVAILABLE', 42, 1);
INSERT INTO public.facility_maintenance VALUES (43, '2026-10-23 14:02:35.344877', 'OPERATION_FAILURE', 'AVAILABLE', 43, 1);
INSERT INTO public.facility_maintenance VALUES (44, '2026-10-23 14:02:35.35362', 'OPERATION_FAILURE', 'AVAILABLE', 44, 1);
INSERT INTO public.facility_maintenance VALUES (45, '2026-10-23 14:02:35.362054', 'OPERATION_FAILURE', 'AVAILABLE', 45, 1);
INSERT INTO public.facility_maintenance VALUES (46, '2026-10-23 14:02:35.368539', 'OPERATION_FAILURE', 'AVAILABLE', 46, 1);
INSERT INTO public.facility_maintenance VALUES (47, '2026-10-23 14:02:35.376327', 'OPERATION_FAILURE', 'AVAILABLE', 47, 1);
INSERT INTO public.facility_maintenance VALUES (48, '2026-10-23 14:02:35.389248', 'OPERATION_FAILURE', 'AVAILABLE', 48, 1);
INSERT INTO public.facility_maintenance VALUES (49, '2026-10-23 14:02:35.397925', 'OPERATION_FAILURE', 'AVAILABLE', 49, 1);
INSERT INTO public.facility_maintenance VALUES (50, '2026-10-23 14:02:35.405713', 'OPERATION_FAILURE', 'AVAILABLE', 50, 1);
INSERT INTO public.facility_maintenance VALUES (51, '2026-10-23 14:02:35.414089', 'OPERATION_FAILURE', 'AVAILABLE', 51, 1);
INSERT INTO public.facility_maintenance VALUES (52, '2026-10-23 14:02:35.422391', 'OPERATION_FAILURE', 'AVAILABLE', 52, 1);
INSERT INTO public.facility_maintenance VALUES (53, '2026-10-23 14:02:35.432527', 'OPERATION_FAILURE', 'AVAILABLE', 53, 1);
INSERT INTO public.facility_maintenance VALUES (54, '2026-10-23 14:02:35.442067', 'OPERATION_FAILURE', 'AVAILABLE', 54, 1);
INSERT INTO public.facility_maintenance VALUES (55, '2026-10-23 14:02:35.449609', 'OPERATION_FAILURE', 'AVAILABLE', 55, 1);
INSERT INTO public.facility_maintenance VALUES (56, '2026-10-23 14:02:35.459406', 'OPERATION_FAILURE', 'AVAILABLE', 56, 1);
INSERT INTO public.facility_maintenance VALUES (57, '2026-10-23 14:02:35.468918', 'OPERATION_FAILURE', 'AVAILABLE', 57, 1);
INSERT INTO public.facility_maintenance VALUES (58, '2026-10-23 14:02:35.480321', 'OPERATION_FAILURE', 'AVAILABLE', 58, 1);
INSERT INTO public.facility_maintenance VALUES (59, '2026-10-23 14:02:35.490502', 'OPERATION_FAILURE', 'AVAILABLE', 59, 1);
INSERT INTO public.facility_maintenance VALUES (60, '2026-10-23 14:02:35.499369', 'OPERATION_FAILURE', 'AVAILABLE', 60, 1);
INSERT INTO public.facility_maintenance VALUES (61, '2026-10-23 14:02:35.508345', 'OPERATION_FAILURE', 'AVAILABLE', 61, 1);
INSERT INTO public.facility_maintenance VALUES (62, '2026-10-23 14:02:35.512674', 'OPERATION_FAILURE', 'AVAILABLE', 62, 1);
INSERT INTO public.facility_maintenance VALUES (63, '2026-10-23 14:02:35.529605', 'OPERATION_FAILURE', 'AVAILABLE', 63, 1);
INSERT INTO public.facility_maintenance VALUES (64, '2026-10-23 14:02:35.536622', 'OPERATION_FAILURE', 'AVAILABLE', 64, 1);
INSERT INTO public.facility_maintenance VALUES (65, '2026-10-23 14:02:35.552076', 'OPERATION_FAILURE', 'AVAILABLE', 65, 1);
INSERT INTO public.facility_maintenance VALUES (66, '2026-10-23 14:02:35.56153', 'OPERATION_FAILURE', 'AVAILABLE', 66, 1);
INSERT INTO public.facility_maintenance VALUES (67, '2026-10-23 14:02:35.572636', 'OPERATION_FAILURE', 'AVAILABLE', 67, 1);
INSERT INTO public.facility_maintenance VALUES (68, '2026-10-23 14:02:35.575787', 'OPERATION_FAILURE', 'AVAILABLE', 68, 1);
INSERT INTO public.facility_maintenance VALUES (69, '2026-10-23 14:02:35.590138', 'OPERATION_FAILURE', 'AVAILABLE', 69, 1);
INSERT INTO public.facility_maintenance VALUES (70, '2026-10-23 14:02:35.596674', 'OPERATION_FAILURE', 'AVAILABLE', 70, 1);
INSERT INTO public.facility_maintenance VALUES (71, '2026-10-23 14:02:35.601586', 'OPERATION_FAILURE', 'AVAILABLE', 71, 1);
INSERT INTO public.facility_maintenance VALUES (72, '2026-10-23 14:02:35.61201', 'OPERATION_FAILURE', 'AVAILABLE', 72, 1);
INSERT INTO public.facility_maintenance VALUES (73, '2026-10-23 14:02:35.620744', 'OPERATION_FAILURE', 'AVAILABLE', 73, 1);
INSERT INTO public.facility_maintenance VALUES (74, '2026-10-23 14:02:35.630482', 'OPERATION_FAILURE', 'AVAILABLE', 74, 1);
INSERT INTO public.facility_maintenance VALUES (75, '2026-10-23 14:02:35.639759', 'OPERATION_FAILURE', 'AVAILABLE', 75, 1);
INSERT INTO public.facility_maintenance VALUES (76, '2026-10-23 14:02:35.65053', 'OPERATION_FAILURE', 'AVAILABLE', 76, 1);
INSERT INTO public.facility_maintenance VALUES (77, '2026-10-23 14:02:35.660785', 'OPERATION_FAILURE', 'AVAILABLE', 77, 1);
INSERT INTO public.facility_maintenance VALUES (78, '2026-10-23 14:02:35.670075', 'OPERATION_FAILURE', 'AVAILABLE', 78, 1);
INSERT INTO public.facility_maintenance VALUES (79, '2026-10-23 14:02:35.680621', 'OPERATION_FAILURE', 'AVAILABLE', 79, 1);
INSERT INTO public.facility_maintenance VALUES (80, '2026-10-23 14:02:35.689161', 'OPERATION_FAILURE', 'AVAILABLE', 80, 1);
INSERT INTO public.facility_maintenance VALUES (81, '2026-10-23 14:02:35.698683', 'OPERATION_FAILURE', 'AVAILABLE', 81, 1);
INSERT INTO public.facility_maintenance VALUES (82, '2026-10-23 14:02:35.708836', 'OPERATION_FAILURE', 'AVAILABLE', 82, 1);
INSERT INTO public.facility_maintenance VALUES (83, '2026-10-23 14:02:35.71735', 'OPERATION_FAILURE', 'AVAILABLE', 83, 1);
INSERT INTO public.facility_maintenance VALUES (84, '2026-10-23 14:02:35.728922', 'OPERATION_FAILURE', 'AVAILABLE', 84, 1);
INSERT INTO public.facility_maintenance VALUES (85, '2026-10-23 14:02:35.73776', 'OPERATION_FAILURE', 'AVAILABLE', 85, 1);
INSERT INTO public.facility_maintenance VALUES (86, '2026-10-23 14:02:35.750386', 'OPERATION_FAILURE', 'AVAILABLE', 86, 1);
INSERT INTO public.facility_maintenance VALUES (87, '2026-10-23 14:02:35.76083', 'OPERATION_FAILURE', 'AVAILABLE', 87, 1);
INSERT INTO public.facility_maintenance VALUES (88, '2026-10-23 14:02:35.767272', 'OPERATION_FAILURE', 'AVAILABLE', 88, 1);
INSERT INTO public.facility_maintenance VALUES (89, '2026-10-23 14:02:35.779098', 'OPERATION_FAILURE', 'AVAILABLE', 89, 1);
INSERT INTO public.facility_maintenance VALUES (90, '2026-10-23 14:02:35.79152', 'OPERATION_FAILURE', 'AVAILABLE', 90, 1);
INSERT INTO public.facility_maintenance VALUES (91, '2026-10-23 14:02:35.797628', 'OPERATION_FAILURE', 'AVAILABLE', 91, 1);
INSERT INTO public.facility_maintenance VALUES (92, '2026-10-23 14:02:35.803154', 'OPERATION_FAILURE', 'AVAILABLE', 92, 1);
INSERT INTO public.facility_maintenance VALUES (93, '2026-10-23 14:02:35.810402', 'OPERATION_FAILURE', 'AVAILABLE', 93, 1);
INSERT INTO public.facility_maintenance VALUES (94, '2026-10-23 14:02:35.817932', 'OPERATION_FAILURE', 'AVAILABLE', 94, 1);
INSERT INTO public.facility_maintenance VALUES (95, '2026-10-23 14:02:35.826333', 'OPERATION_FAILURE', 'AVAILABLE', 95, 1);


--
-- TOC entry 5076 (class 0 OID 19296)
-- Dependencies: 232
-- Data for Name: notices; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.notices VALUES (1, '더미 qna 내용 1
입니다.', '2026-10-23 14:01:26.286188', '더미글 1', '2026-10-23 14:01:26.286188', 1);
INSERT INTO public.notices VALUES (2, '더미 qna 내용 2
입니다.', '2026-10-23 14:01:26.378899', '더미글 2', '2026-10-23 14:01:26.378899', 1);
INSERT INTO public.notices VALUES (3, '더미 qna 내용 3
입니다.', '2026-10-23 14:01:26.390459', '더미글 3', '2026-10-23 14:01:26.390459', 1);
INSERT INTO public.notices VALUES (4, '더미 qna 내용 4
입니다.', '2026-10-23 14:01:26.398673', '더미글 4', '2026-10-23 14:01:26.398673', 1);
INSERT INTO public.notices VALUES (5, '더미 qna 내용 5
입니다.', '2026-10-23 14:01:26.405723', '더미글 5', '2026-10-23 14:01:26.405723', 1);
INSERT INTO public.notices VALUES (6, '더미 qna 내용 6
입니다.', '2026-10-23 14:01:26.415893', '더미글 6', '2026-10-23 14:01:26.415893', 1);
INSERT INTO public.notices VALUES (7, '더미 qna 내용 7
입니다.', '2026-10-23 14:01:26.422973', '더미글 7', '2026-10-23 14:01:26.422973', 1);
INSERT INTO public.notices VALUES (8, '더미 qna 내용 8
입니다.', '2026-10-23 14:01:26.4322', '더미글 8', '2026-10-23 14:01:26.4322', 1);
INSERT INTO public.notices VALUES (9, '더미 qna 내용 9
입니다.', '2026-10-23 14:01:26.439617', '더미글 9', '2026-10-23 14:01:26.439617', 1);
INSERT INTO public.notices VALUES (10, '더미 qna 내용 10
입니다.', '2026-10-23 14:01:26.448098', '더미글 10', '2026-10-23 14:01:26.448098', 1);
INSERT INTO public.notices VALUES (11, '더미 qna 내용 11
입니다.', '2026-10-23 14:01:26.455918', '더미글 11', '2026-10-23 14:01:26.455918', 1);
INSERT INTO public.notices VALUES (12, '더미 qna 내용 12
입니다.', '2026-10-23 14:01:26.463591', '더미글 12', '2026-10-23 14:01:26.463591', 1);
INSERT INTO public.notices VALUES (13, '더미 qna 내용 13
입니다.', '2026-10-23 14:01:26.470886', '더미글 13', '2026-10-23 14:01:26.470886', 1);
INSERT INTO public.notices VALUES (14, '더미 qna 내용 14
입니다.', '2026-10-23 14:01:26.478415', '더미글 14', '2026-10-23 14:01:26.478415', 1);
INSERT INTO public.notices VALUES (15, '더미 qna 내용 15
입니다.', '2026-10-23 14:01:26.485811', '더미글 15', '2026-10-23 14:01:26.485811', 1);
INSERT INTO public.notices VALUES (16, '더미 qna 내용 16
입니다.', '2026-10-23 14:01:26.49402', '더미글 16', '2026-10-23 14:01:26.49402', 1);
INSERT INTO public.notices VALUES (17, '더미 qna 내용 17
입니다.', '2026-10-23 14:01:26.501718', '더미글 17', '2026-10-23 14:01:26.501718', 1);
INSERT INTO public.notices VALUES (18, '더미 qna 내용 18
입니다.', '2026-10-23 14:01:26.508813', '더미글 18', '2026-10-23 14:01:26.508813', 1);
INSERT INTO public.notices VALUES (19, '더미 qna 내용 19
입니다.', '2026-10-23 14:01:26.516347', '더미글 19', '2026-10-23 14:01:26.516347', 1);
INSERT INTO public.notices VALUES (20, '더미 qna 내용 20
입니다.', '2026-10-23 14:01:26.522325', '더미글 20', '2026-10-23 14:01:26.522325', 1);
INSERT INTO public.notices VALUES (21, '더미 qna 내용 21
입니다.', '2026-10-23 14:01:26.529414', '더미글 21', '2026-10-23 14:01:26.529414', 1);
INSERT INTO public.notices VALUES (22, '더미 qna 내용 22
입니다.', '2026-10-23 14:01:26.535005', '더미글 22', '2026-10-23 14:01:26.535005', 1);
INSERT INTO public.notices VALUES (23, '더미 qna 내용 23
입니다.', '2026-10-23 14:01:26.541791', '더미글 23', '2026-10-23 14:01:26.541791', 1);
INSERT INTO public.notices VALUES (24, '더미 qna 내용 24
입니다.', '2026-10-23 14:01:26.549833', '더미글 24', '2026-10-23 14:01:26.549833', 1);
INSERT INTO public.notices VALUES (25, '더미 qna 내용 25
입니다.', '2026-10-23 14:01:26.557208', '더미글 25', '2026-10-23 14:01:26.557208', 1);
INSERT INTO public.notices VALUES (26, '더미 qna 내용 26
입니다.', '2026-10-23 14:01:26.565776', '더미글 26', '2026-10-23 14:01:26.565776', 1);
INSERT INTO public.notices VALUES (27, '더미 qna 내용 27
입니다.', '2026-10-23 14:01:26.57411', '더미글 27', '2026-10-23 14:01:26.57411', 1);
INSERT INTO public.notices VALUES (28, '더미 qna 내용 28
입니다.', '2026-10-23 14:01:26.582194', '더미글 28', '2026-10-23 14:01:26.582194', 1);
INSERT INTO public.notices VALUES (29, '더미 qna 내용 29
입니다.', '2026-10-23 14:01:26.589333', '더미글 29', '2026-10-23 14:01:26.589333', 1);
INSERT INTO public.notices VALUES (30, '더미 qna 내용 30
입니다.', '2026-10-23 14:01:26.598487', '더미글 30', '2026-10-23 14:01:26.598487', 1);
INSERT INTO public.notices VALUES (31, '더미 qna 내용 31
입니다.', '2026-10-23 14:01:26.6056', '더미글 31', '2026-10-23 14:01:26.6056', 1);
INSERT INTO public.notices VALUES (32, '더미 qna 내용 32
입니다.', '2026-10-23 14:01:26.613614', '더미글 32', '2026-10-23 14:01:26.613614', 1);
INSERT INTO public.notices VALUES (33, '더미 qna 내용 33
입니다.', '2026-10-23 14:01:26.621033', '더미글 33', '2026-10-23 14:01:26.621033', 1);
INSERT INTO public.notices VALUES (34, '더미 qna 내용 34
입니다.', '2026-10-23 14:01:26.628579', '더미글 34', '2026-10-23 14:01:26.628579', 1);
INSERT INTO public.notices VALUES (35, '더미 qna 내용 35
입니다.', '2026-10-23 14:01:26.636178', '더미글 35', '2026-10-23 14:01:26.636178', 1);
INSERT INTO public.notices VALUES (36, '더미 qna 내용 36
입니다.', '2026-10-23 14:01:26.643174', '더미글 36', '2026-10-23 14:01:26.643174', 1);
INSERT INTO public.notices VALUES (37, '더미 qna 내용 37
입니다.', '2026-10-23 14:01:26.649934', '더미글 37', '2026-10-23 14:01:26.649934', 1);
INSERT INTO public.notices VALUES (38, '더미 qna 내용 38
입니다.', '2026-10-23 14:01:26.657285', '더미글 38', '2026-10-23 14:01:26.657285', 1);
INSERT INTO public.notices VALUES (39, '더미 qna 내용 39
입니다.', '2026-10-23 14:01:26.665409', '더미글 39', '2026-10-23 14:01:26.665409', 1);
INSERT INTO public.notices VALUES (40, '더미 qna 내용 40
입니다.', '2026-10-23 14:01:26.672455', '더미글 40', '2026-10-23 14:01:26.672455', 1);
INSERT INTO public.notices VALUES (41, '더미 qna 내용 41
입니다.', '2026-10-23 14:01:26.680418', '더미글 41', '2026-10-23 14:01:26.680418', 1);
INSERT INTO public.notices VALUES (42, '더미 qna 내용 42
입니다.', '2026-10-23 14:01:26.687702', '더미글 42', '2026-10-23 14:01:26.687702', 1);
INSERT INTO public.notices VALUES (43, '더미 qna 내용 43
입니다.', '2026-10-23 14:01:26.69396', '더미글 43', '2026-10-23 14:01:26.69396', 1);
INSERT INTO public.notices VALUES (44, '더미 qna 내용 44
입니다.', '2026-10-23 14:01:26.702249', '더미글 44', '2026-10-23 14:01:26.702249', 1);
INSERT INTO public.notices VALUES (45, '더미 qna 내용 45
입니다.', '2026-10-23 14:01:26.709818', '더미글 45', '2026-10-23 14:01:26.709818', 1);
INSERT INTO public.notices VALUES (46, '더미 qna 내용 46
입니다.', '2026-10-23 14:01:26.716674', '더미글 46', '2026-10-23 14:01:26.716674', 1);
INSERT INTO public.notices VALUES (47, '더미 qna 내용 47
입니다.', '2026-10-23 14:01:26.723721', '더미글 47', '2026-10-23 14:01:26.723721', 1);
INSERT INTO public.notices VALUES (48, '더미 qna 내용 48
입니다.', '2026-10-23 14:01:26.727514', '더미글 48', '2026-10-23 14:01:26.727514', 1);
INSERT INTO public.notices VALUES (49, '더미 qna 내용 49
입니다.', '2026-10-23 14:01:26.734213', '더미글 49', '2026-10-23 14:01:26.734213', 1);
INSERT INTO public.notices VALUES (50, '더미 qna 내용 50
입니다.', '2026-10-23 14:01:26.739138', '더미글 50', '2026-10-23 14:01:26.739138', 1);
INSERT INTO public.notices VALUES (51, '더미 qna 내용 51
입니다.', '2026-10-23 14:01:26.747337', '더미글 51', '2026-10-23 14:01:26.747337', 1);
INSERT INTO public.notices VALUES (52, '더미 qna 내용 52
입니다.', '2026-10-23 14:01:26.755493', '더미글 52', '2026-10-23 14:01:26.755493', 1);
INSERT INTO public.notices VALUES (53, '더미 qna 내용 53
입니다.', '2026-10-23 14:01:26.763132', '더미글 53', '2026-10-23 14:01:26.763132', 1);
INSERT INTO public.notices VALUES (54, '더미 qna 내용 54
입니다.', '2026-10-23 14:01:26.769901', '더미글 54', '2026-10-23 14:01:26.769901', 1);
INSERT INTO public.notices VALUES (55, '더미 qna 내용 55
입니다.', '2026-10-23 14:01:26.776643', '더미글 55', '2026-10-23 14:01:26.776643', 1);
INSERT INTO public.notices VALUES (56, '더미 qna 내용 56
입니다.', '2026-10-23 14:01:26.783701', '더미글 56', '2026-10-23 14:01:26.783701', 1);
INSERT INTO public.notices VALUES (57, '더미 qna 내용 57
입니다.', '2026-10-23 14:01:26.791232', '더미글 57', '2026-10-23 14:01:26.791232', 1);
INSERT INTO public.notices VALUES (58, '더미 qna 내용 58
입니다.', '2026-10-23 14:01:26.798522', '더미글 58', '2026-10-23 14:01:26.798522', 1);
INSERT INTO public.notices VALUES (59, '더미 qna 내용 59
입니다.', '2026-10-23 14:01:26.805855', '더미글 59', '2026-10-23 14:01:26.805855', 1);
INSERT INTO public.notices VALUES (60, '더미 qna 내용 60
입니다.', '2026-10-23 14:01:26.81087', '더미글 60', '2026-10-23 14:01:26.81087', 1);
INSERT INTO public.notices VALUES (61, '더미 qna 내용 61
입니다.', '2026-10-23 14:01:26.81545', '더미글 61', '2026-10-23 14:01:26.81545', 1);
INSERT INTO public.notices VALUES (62, '더미 qna 내용 62
입니다.', '2026-10-23 14:01:26.822535', '더미글 62', '2026-10-23 14:01:26.822535', 1);
INSERT INTO public.notices VALUES (63, '더미 qna 내용 63
입니다.', '2026-10-23 14:01:26.830846', '더미글 63', '2026-10-23 14:01:26.830846', 1);
INSERT INTO public.notices VALUES (64, '더미 qna 내용 64
입니다.', '2026-10-23 14:01:26.837866', '더미글 64', '2026-10-23 14:01:26.837866', 1);
INSERT INTO public.notices VALUES (65, '더미 qna 내용 65
입니다.', '2026-10-23 14:01:26.847668', '더미글 65', '2026-10-23 14:01:26.847668', 1);
INSERT INTO public.notices VALUES (66, '더미 qna 내용 66
입니다.', '2026-10-23 14:01:26.854496', '더미글 66', '2026-10-23 14:01:26.854496', 1);
INSERT INTO public.notices VALUES (67, '더미 qna 내용 67
입니다.', '2026-10-23 14:01:26.861415', '더미글 67', '2026-10-23 14:01:26.861415', 1);
INSERT INTO public.notices VALUES (68, '더미 qna 내용 68
입니다.', '2026-10-23 14:01:26.868016', '더미글 68', '2026-10-23 14:01:26.868016', 1);
INSERT INTO public.notices VALUES (69, '더미 qna 내용 69
입니다.', '2026-10-23 14:01:26.874495', '더미글 69', '2026-10-23 14:01:26.874495', 1);
INSERT INTO public.notices VALUES (70, '더미 qna 내용 70
입니다.', '2026-10-23 14:01:26.882155', '더미글 70', '2026-10-23 14:01:26.882155', 1);
INSERT INTO public.notices VALUES (71, '더미 qna 내용 71
입니다.', '2026-10-23 14:01:26.889092', '더미글 71', '2026-10-23 14:01:26.889092', 1);
INSERT INTO public.notices VALUES (72, '더미 qna 내용 72
입니다.', '2026-10-23 14:01:26.896916', '더미글 72', '2026-10-23 14:01:26.896916', 1);
INSERT INTO public.notices VALUES (73, '더미 qna 내용 73
입니다.', '2026-10-23 14:01:26.903932', '더미글 73', '2026-10-23 14:01:26.903932', 1);
INSERT INTO public.notices VALUES (74, '더미 qna 내용 74
입니다.', '2026-10-23 14:01:26.910391', '더미글 74', '2026-10-23 14:01:26.910391', 1);
INSERT INTO public.notices VALUES (75, '더미 qna 내용 75
입니다.', '2026-10-23 14:01:26.915655', '더미글 75', '2026-10-23 14:01:26.915655', 1);
INSERT INTO public.notices VALUES (76, '더미 qna 내용 76
입니다.', '2026-10-23 14:01:26.920679', '더미글 76', '2026-10-23 14:01:26.920679', 1);
INSERT INTO public.notices VALUES (77, '더미 qna 내용 77
입니다.', '2026-10-23 14:01:26.927103', '더미글 77', '2026-10-23 14:01:26.927103', 1);
INSERT INTO public.notices VALUES (78, '더미 qna 내용 78
입니다.', '2026-10-23 14:01:26.935404', '더미글 78', '2026-10-23 14:01:26.935404', 1);
INSERT INTO public.notices VALUES (79, '더미 qna 내용 79
입니다.', '2026-10-23 14:01:26.940423', '더미글 79', '2026-10-23 14:01:26.940423', 1);
INSERT INTO public.notices VALUES (80, '더미 qna 내용 80
입니다.', '2026-10-23 14:01:26.948877', '더미글 80', '2026-10-23 14:01:26.948877', 1);
INSERT INTO public.notices VALUES (81, '더미 qna 내용 81
입니다.', '2026-10-23 14:01:26.955887', '더미글 81', '2026-10-23 14:01:26.955887', 1);
INSERT INTO public.notices VALUES (82, '더미 qna 내용 82
입니다.', '2026-10-23 14:01:26.963434', '더미글 82', '2026-10-23 14:01:26.963434', 1);
INSERT INTO public.notices VALUES (83, '더미 qna 내용 83
입니다.', '2026-10-23 14:01:26.969924', '더미글 83', '2026-10-23 14:01:26.969924', 1);
INSERT INTO public.notices VALUES (84, '더미 qna 내용 84
입니다.', '2026-10-23 14:01:26.977156', '더미글 84', '2026-10-23 14:01:26.977156', 1);
INSERT INTO public.notices VALUES (85, '더미 qna 내용 85
입니다.', '2026-10-23 14:01:26.983662', '더미글 85', '2026-10-23 14:01:26.983662', 1);
INSERT INTO public.notices VALUES (86, '더미 qna 내용 86
입니다.', '2026-10-23 14:01:26.990732', '더미글 86', '2026-10-23 14:01:26.990732', 1);
INSERT INTO public.notices VALUES (87, '더미 qna 내용 87
입니다.', '2026-10-23 14:01:26.999075', '더미글 87', '2026-10-23 14:01:26.999075', 1);
INSERT INTO public.notices VALUES (88, '더미 qna 내용 88
입니다.', '2026-10-23 14:01:27.0059', '더미글 88', '2026-10-23 14:01:27.0059', 1);
INSERT INTO public.notices VALUES (89, '더미 qna 내용 89
입니다.', '2026-10-23 14:01:27.013861', '더미글 89', '2026-10-23 14:01:27.013861', 1);
INSERT INTO public.notices VALUES (90, '더미 qna 내용 90
입니다.', '2026-10-23 14:01:27.020698', '더미글 90', '2026-10-23 14:01:27.020698', 1);
INSERT INTO public.notices VALUES (91, '더미 qna 내용 91
입니다.', '2026-10-23 14:01:27.027319', '더미글 91', '2026-10-23 14:01:27.027319', 1);
INSERT INTO public.notices VALUES (92, '더미 qna 내용 92
입니다.', '2026-10-23 14:01:27.034349', '더미글 92', '2026-10-23 14:01:27.034349', 1);
INSERT INTO public.notices VALUES (93, '더미 qna 내용 93
입니다.', '2026-10-23 14:01:27.040832', '더미글 93', '2026-10-23 14:01:27.040832', 1);
INSERT INTO public.notices VALUES (94, '더미 qna 내용 94
입니다.', '2026-10-23 14:01:27.048131', '더미글 94', '2026-10-23 14:01:27.048131', 1);
INSERT INTO public.notices VALUES (95, '더미 qna 내용 95
입니다.', '2026-10-23 14:01:27.054719', '더미글 95', '2026-10-23 14:01:27.054719', 1);
INSERT INTO public.notices VALUES (96, '더미 qna 내용 96
입니다.', '2026-10-23 14:01:27.062474', '더미글 96', '2026-10-23 14:01:27.062474', 1);
INSERT INTO public.notices VALUES (97, '더미 qna 내용 97
입니다.', '2026-10-23 14:01:27.069452', '더미글 97', '2026-10-23 14:01:27.069452', 1);
INSERT INTO public.notices VALUES (98, '더미 qna 내용 98
입니다.', '2026-10-23 14:01:27.076053', '더미글 98', '2026-10-23 14:01:27.076053', 1);
INSERT INTO public.notices VALUES (99, '더미 qna 내용 99
입니다.', '2026-10-23 14:01:27.082638', '더미글 99', '2026-10-23 14:01:27.082638', 1);
INSERT INTO public.notices VALUES (100, '더미 qna 내용 100
입니다.', '2026-10-23 14:01:27.089688', '더미글 100', '2026-10-23 14:01:27.089688', 1);


--
-- TOC entry 5078 (class 0 OID 19309)
-- Dependencies: 234
-- Data for Name: qna; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.qna VALUES (51, NULL, NULL, '더미 qna 내용 51
입니다.', '2026-10-23 13:59:57.991243', 0, 'WAITING', '더미글 51', '2026-10-23 13:59:57.991243', 1);
INSERT INTO public.qna VALUES (52, NULL, NULL, '더미 qna 내용 52
입니다.', '2026-10-23 13:59:57.999352', 0, 'WAITING', '더미글 52', '2026-10-23 13:59:57.999352', 1);
INSERT INTO public.qna VALUES (53, NULL, NULL, '더미 qna 내용 53
입니다.', '2026-10-23 13:59:58.007363', 0, 'WAITING', '더미글 53', '2026-10-23 13:59:58.007363', 1);
INSERT INTO public.qna VALUES (54, NULL, NULL, '더미 qna 내용 54
입니다.', '2026-10-23 13:59:58.015698', 0, 'WAITING', '더미글 54', '2026-10-23 13:59:58.015698', 1);
INSERT INTO public.qna VALUES (55, NULL, NULL, '더미 qna 내용 55
입니다.', '2026-10-23 13:59:58.024486', 0, 'WAITING', '더미글 55', '2026-10-23 13:59:58.024486', 1);
INSERT INTO public.qna VALUES (56, NULL, NULL, '더미 qna 내용 56
입니다.', '2026-10-23 13:59:58.032009', 0, 'WAITING', '더미글 56', '2026-10-23 13:59:58.032009', 1);
INSERT INTO public.qna VALUES (57, NULL, NULL, '더미 qna 내용 57
입니다.', '2026-10-23 13:59:58.039944', 0, 'WAITING', '더미글 57', '2026-10-23 13:59:58.039944', 1);
INSERT INTO public.qna VALUES (58, NULL, NULL, '더미 qna 내용 58
입니다.', '2026-10-23 13:59:58.048673', 0, 'WAITING', '더미글 58', '2026-10-23 13:59:58.048673', 1);
INSERT INTO public.qna VALUES (59, NULL, NULL, '더미 qna 내용 59
입니다.', '2026-10-23 13:59:58.055685', 0, 'WAITING', '더미글 59', '2026-10-23 13:59:58.055685', 1);
INSERT INTO public.qna VALUES (60, NULL, NULL, '더미 qna 내용 60
입니다.', '2026-10-23 13:59:58.063909', 0, 'WAITING', '더미글 60', '2026-10-23 13:59:58.063909', 1);
INSERT INTO public.qna VALUES (61, NULL, NULL, '더미 qna 내용 61
입니다.', '2026-10-23 13:59:58.070421', 0, 'WAITING', '더미글 61', '2026-10-23 13:59:58.070421', 1);
INSERT INTO public.qna VALUES (2, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:02.825209', '더미 qna 내용 2
입니다.', '2026-10-23 13:59:57.573436', 0, 'ANSWERED', '더미글 2', '2026-10-23 14:03:02.828206', 1);
INSERT INTO public.qna VALUES (4, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:02.846263', '더미 qna 내용 4
입니다.', '2026-10-23 13:59:57.606932', 0, 'ANSWERED', '더미글 4', '2026-10-23 14:03:02.850268', 1);
INSERT INTO public.qna VALUES (5, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:02.857797', '더미 qna 내용 5
입니다.', '2026-10-23 13:59:57.615365', 0, 'ANSWERED', '더미글 5', '2026-10-23 14:03:02.8618', 1);
INSERT INTO public.qna VALUES (7, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:02.884959', '더미 qna 내용 7
입니다.', '2026-10-23 13:59:57.631674', 0, 'ANSWERED', '더미글 7', '2026-10-23 14:03:02.889964', 1);
INSERT INTO public.qna VALUES (8, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:02.897961', '더미 qna 내용 8
입니다.', '2026-10-23 13:59:57.6402', 0, 'ANSWERED', '더미글 8', '2026-10-23 14:03:02.909486', 1);
INSERT INTO public.qna VALUES (10, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:02.929969', '더미 qna 내용 10
입니다.', '2026-10-23 13:59:57.658953', 0, 'ANSWERED', '더미글 10', '2026-10-23 14:03:02.935974', 1);
INSERT INTO public.qna VALUES (11, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:02.944516', '더미 qna 내용 11
입니다.', '2026-10-23 13:59:57.667494', 0, 'ANSWERED', '더미글 11', '2026-10-23 14:03:02.94751', 1);
INSERT INTO public.qna VALUES (13, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:02.966632', '더미 qna 내용 13
입니다.', '2026-10-23 13:59:57.684035', 0, 'ANSWERED', '더미글 13', '2026-10-23 14:03:02.96915', 1);
INSERT INTO public.qna VALUES (14, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:02.977621', '더미 qna 내용 14
입니다.', '2026-10-23 13:59:57.692558', 0, 'ANSWERED', '더미글 14', '2026-10-23 14:03:02.980625', 1);
INSERT INTO public.qna VALUES (16, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:02.998661', '더미 qna 내용 16
입니다.', '2026-10-23 13:59:57.710435', 0, 'ANSWERED', '더미글 16', '2026-10-23 14:03:03.002663', 1);
INSERT INTO public.qna VALUES (17, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.011191', '더미 qna 내용 17
입니다.', '2026-10-23 13:59:57.715941', 0, 'ANSWERED', '더미글 17', '2026-10-23 14:03:03.013189', 1);
INSERT INTO public.qna VALUES (19, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.029247', '더미 qna 내용 19
입니다.', '2026-10-23 13:59:57.731228', 0, 'ANSWERED', '더미글 19', '2026-10-23 14:03:03.032249', 1);
INSERT INTO public.qna VALUES (20, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.037477', '더미 qna 내용 20
입니다.', '2026-10-23 13:59:57.738623', 0, 'ANSWERED', '더미글 20', '2026-10-23 14:03:03.040979', 1);
INSERT INTO public.qna VALUES (22, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.059068', '더미 qna 내용 22
입니다.', '2026-10-23 13:59:57.755275', 0, 'ANSWERED', '더미글 22', '2026-10-23 14:03:03.062062', 1);
INSERT INTO public.qna VALUES (24, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.080123', '더미 qna 내용 24
입니다.', '2026-10-23 13:59:57.773096', 0, 'ANSWERED', '더미글 24', '2026-10-23 14:03:03.082127', 1);
INSERT INTO public.qna VALUES (25, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.088972', '더미 qna 내용 25
입니다.', '2026-10-23 13:59:57.781556', 0, 'ANSWERED', '더미글 25', '2026-10-23 14:03:03.091655', 1);
INSERT INTO public.qna VALUES (27, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.104178', '더미 qna 내용 27
입니다.', '2026-10-23 13:59:57.798258', 0, 'ANSWERED', '더미글 27', '2026-10-23 14:03:03.108756', 1);
INSERT INTO public.qna VALUES (28, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.115084', '더미 qna 내용 28
입니다.', '2026-10-23 13:59:57.805636', 0, 'ANSWERED', '더미글 28', '2026-10-23 14:03:03.118098', 1);
INSERT INTO public.qna VALUES (30, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.131804', '더미 qna 내용 30
입니다.', '2026-10-23 13:59:57.82206', 0, 'ANSWERED', '더미글 30', '2026-10-23 14:03:03.133819', 1);
INSERT INTO public.qna VALUES (31, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.141182', '더미 qna 내용 31
입니다.', '2026-10-23 13:59:57.830436', 0, 'ANSWERED', '더미글 31', '2026-10-23 14:03:03.144195', 1);
INSERT INTO public.qna VALUES (33, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.166883', '더미 qna 내용 33
입니다.', '2026-10-23 13:59:57.847349', 0, 'ANSWERED', '더미글 33', '2026-10-23 14:03:03.168888', 1);
INSERT INTO public.qna VALUES (34, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.175857', '더미 qna 내용 34
입니다.', '2026-10-23 13:59:57.855632', 0, 'ANSWERED', '더미글 34', '2026-10-23 14:03:03.178853', 1);
INSERT INTO public.qna VALUES (36, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.195466', '더미 qna 내용 36
입니다.', '2026-10-23 13:59:57.872265', 0, 'ANSWERED', '더미글 36', '2026-10-23 14:03:03.197479', 1);
INSERT INTO public.qna VALUES (37, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.204345', '더미 qna 내용 37
입니다.', '2026-10-23 13:59:57.880826', 0, 'ANSWERED', '더미글 37', '2026-10-23 14:03:03.20732', 1);
INSERT INTO public.qna VALUES (39, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.222226', '더미 qna 내용 39
입니다.', '2026-10-23 13:59:57.897262', 0, 'ANSWERED', '더미글 39', '2026-10-23 14:03:03.225757', 1);
INSERT INTO public.qna VALUES (40, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.230785', '더미 qna 내용 40
입니다.', '2026-10-23 13:59:57.905769', 0, 'ANSWERED', '더미글 40', '2026-10-23 14:03:03.232952', 1);
INSERT INTO public.qna VALUES (42, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.246201', '더미 qna 내용 42
입니다.', '2026-10-23 13:59:57.921195', 0, 'ANSWERED', '더미글 42', '2026-10-23 14:03:03.249199', 1);
INSERT INTO public.qna VALUES (43, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.256221', '더미 qna 내용 43
입니다.', '2026-10-23 13:59:57.929256', 0, 'ANSWERED', '더미글 43', '2026-10-23 14:03:03.258216', 1);
INSERT INTO public.qna VALUES (45, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.274753', '더미 qna 내용 45
입니다.', '2026-10-23 13:59:57.942339', 0, 'ANSWERED', '더미글 45', '2026-10-23 14:03:03.277085', 1);
INSERT INTO public.qna VALUES (47, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.288615', '더미 qna 내용 47
입니다.', '2026-10-23 13:59:57.958752', 0, 'ANSWERED', '더미글 47', '2026-10-23 14:03:03.291613', 1);
INSERT INTO public.qna VALUES (48, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.29787', '더미 qna 내용 48
입니다.', '2026-10-23 13:59:57.966339', 0, 'ANSWERED', '더미글 48', '2026-10-23 14:03:03.299766', 1);
INSERT INTO public.qna VALUES (50, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.315378', '더미 qna 내용 50
입니다.', '2026-10-23 13:59:57.983835', 0, 'ANSWERED', '더미글 50', '2026-10-23 14:03:03.318515', 1);
INSERT INTO public.qna VALUES (62, NULL, NULL, '더미 qna 내용 62
입니다.', '2026-10-23 13:59:58.079424', 0, 'WAITING', '더미글 62', '2026-10-23 13:59:58.079424', 1);
INSERT INTO public.qna VALUES (63, NULL, NULL, '더미 qna 내용 63
입니다.', '2026-10-23 13:59:58.088667', 0, 'WAITING', '더미글 63', '2026-10-23 13:59:58.088667', 1);
INSERT INTO public.qna VALUES (64, NULL, NULL, '더미 qna 내용 64
입니다.', '2026-10-23 13:59:58.096369', 0, 'WAITING', '더미글 64', '2026-10-23 13:59:58.096369', 1);
INSERT INTO public.qna VALUES (65, NULL, NULL, '더미 qna 내용 65
입니다.', '2026-10-23 13:59:58.101892', 0, 'WAITING', '더미글 65', '2026-10-23 13:59:58.101892', 1);
INSERT INTO public.qna VALUES (66, NULL, NULL, '더미 qna 내용 66
입니다.', '2026-10-23 13:59:58.105894', 0, 'WAITING', '더미글 66', '2026-10-23 13:59:58.105894', 1);
INSERT INTO public.qna VALUES (67, NULL, NULL, '더미 qna 내용 67
입니다.', '2026-10-23 13:59:58.112422', 0, 'WAITING', '더미글 67', '2026-10-23 13:59:58.112422', 1);
INSERT INTO public.qna VALUES (68, NULL, NULL, '더미 qna 내용 68
입니다.', '2026-10-23 13:59:58.117478', 0, 'WAITING', '더미글 68', '2026-10-23 13:59:58.117478', 1);
INSERT INTO public.qna VALUES (69, NULL, NULL, '더미 qna 내용 69
입니다.', '2026-10-23 13:59:58.122852', 0, 'WAITING', '더미글 69', '2026-10-23 13:59:58.122852', 1);
INSERT INTO public.qna VALUES (70, NULL, NULL, '더미 qna 내용 70
입니다.', '2026-10-23 13:59:58.131382', 0, 'WAITING', '더미글 70', '2026-10-23 13:59:58.131382', 1);
INSERT INTO public.qna VALUES (71, NULL, NULL, '더미 qna 내용 71
입니다.', '2026-10-23 13:59:58.138648', 0, 'WAITING', '더미글 71', '2026-10-23 13:59:58.138648', 1);
INSERT INTO public.qna VALUES (72, NULL, NULL, '더미 qna 내용 72
입니다.', '2026-10-23 13:59:58.148188', 0, 'WAITING', '더미글 72', '2026-10-23 13:59:58.148188', 1);
INSERT INTO public.qna VALUES (73, NULL, NULL, '더미 qna 내용 73
입니다.', '2026-10-23 13:59:58.155712', 0, 'WAITING', '더미글 73', '2026-10-23 13:59:58.155712', 1);
INSERT INTO public.qna VALUES (74, NULL, NULL, '더미 qna 내용 74
입니다.', '2026-10-23 13:59:58.164734', 0, 'WAITING', '더미글 74', '2026-10-23 13:59:58.164734', 1);
INSERT INTO public.qna VALUES (75, NULL, NULL, '더미 qna 내용 75
입니다.', '2026-10-23 13:59:58.173599', 0, 'WAITING', '더미글 75', '2026-10-23 13:59:58.173599', 1);
INSERT INTO public.qna VALUES (76, NULL, NULL, '더미 qna 내용 76
입니다.', '2026-10-23 13:59:58.181608', 0, 'WAITING', '더미글 76', '2026-10-23 13:59:58.181608', 1);
INSERT INTO public.qna VALUES (77, NULL, NULL, '더미 qna 내용 77
입니다.', '2026-10-23 13:59:58.189774', 0, 'WAITING', '더미글 77', '2026-10-23 13:59:58.189774', 1);
INSERT INTO public.qna VALUES (78, NULL, NULL, '더미 qna 내용 78
입니다.', '2026-10-23 13:59:58.197494', 0, 'WAITING', '더미글 78', '2026-10-23 13:59:58.197494', 1);
INSERT INTO public.qna VALUES (79, NULL, NULL, '더미 qna 내용 79
입니다.', '2026-10-23 13:59:58.204497', 0, 'WAITING', '더미글 79', '2026-10-23 13:59:58.204497', 1);
INSERT INTO public.qna VALUES (80, NULL, NULL, '더미 qna 내용 80
입니다.', '2026-10-23 13:59:58.211684', 0, 'WAITING', '더미글 80', '2026-10-23 13:59:58.211684', 1);
INSERT INTO public.qna VALUES (81, NULL, NULL, '더미 qna 내용 81
입니다.', '2026-10-23 13:59:58.218919', 0, 'WAITING', '더미글 81', '2026-10-23 13:59:58.218919', 1);
INSERT INTO public.qna VALUES (82, NULL, NULL, '더미 qna 내용 82
입니다.', '2026-10-23 13:59:58.224914', 0, 'WAITING', '더미글 82', '2026-10-23 13:59:58.224914', 1);
INSERT INTO public.qna VALUES (83, NULL, NULL, '더미 qna 내용 83
입니다.', '2026-10-23 13:59:58.232354', 0, 'WAITING', '더미글 83', '2026-10-23 13:59:58.232354', 1);
INSERT INTO public.qna VALUES (84, NULL, NULL, '더미 qna 내용 84
입니다.', '2026-10-23 13:59:58.238852', 0, 'WAITING', '더미글 84', '2026-10-23 13:59:58.238852', 1);
INSERT INTO public.qna VALUES (85, NULL, NULL, '더미 qna 내용 85
입니다.', '2026-10-23 13:59:58.245384', 0, 'WAITING', '더미글 85', '2026-10-23 13:59:58.245384', 1);
INSERT INTO public.qna VALUES (86, NULL, NULL, '더미 qna 내용 86
입니다.', '2026-10-23 13:59:58.251615', 0, 'WAITING', '더미글 86', '2026-10-23 13:59:58.251615', 1);
INSERT INTO public.qna VALUES (87, NULL, NULL, '더미 qna 내용 87
입니다.', '2026-10-23 13:59:58.25856', 0, 'WAITING', '더미글 87', '2026-10-23 13:59:58.25856', 1);
INSERT INTO public.qna VALUES (88, NULL, NULL, '더미 qna 내용 88
입니다.', '2026-10-23 13:59:58.263558', 0, 'WAITING', '더미글 88', '2026-10-23 13:59:58.263558', 1);
INSERT INTO public.qna VALUES (89, NULL, NULL, '더미 qna 내용 89
입니다.', '2026-10-23 13:59:58.269083', 0, 'WAITING', '더미글 89', '2026-10-23 13:59:58.269083', 1);
INSERT INTO public.qna VALUES (90, NULL, NULL, '더미 qna 내용 90
입니다.', '2026-10-23 13:59:58.275204', 0, 'WAITING', '더미글 90', '2026-10-23 13:59:58.275204', 1);
INSERT INTO public.qna VALUES (91, NULL, NULL, '더미 qna 내용 91
입니다.', '2026-10-23 13:59:58.280542', 0, 'WAITING', '더미글 91', '2026-10-23 13:59:58.280542', 1);
INSERT INTO public.qna VALUES (92, NULL, NULL, '더미 qna 내용 92
입니다.', '2026-10-23 13:59:58.291951', 0, 'WAITING', '더미글 92', '2026-10-23 13:59:58.291951', 1);
INSERT INTO public.qna VALUES (93, NULL, NULL, '더미 qna 내용 93
입니다.', '2026-10-23 13:59:58.296944', 0, 'WAITING', '더미글 93', '2026-10-23 13:59:58.296944', 1);
INSERT INTO public.qna VALUES (94, NULL, NULL, '더미 qna 내용 94
입니다.', '2026-10-23 13:59:58.300993', 0, 'WAITING', '더미글 94', '2026-10-23 13:59:58.300993', 1);
INSERT INTO public.qna VALUES (95, NULL, NULL, '더미 qna 내용 95
입니다.', '2026-10-23 13:59:58.308981', 0, 'WAITING', '더미글 95', '2026-10-23 13:59:58.308981', 1);
INSERT INTO public.qna VALUES (96, NULL, NULL, '더미 qna 내용 96
입니다.', '2026-10-23 13:59:58.315485', 0, 'WAITING', '더미글 96', '2026-10-23 13:59:58.315485', 1);
INSERT INTO public.qna VALUES (97, NULL, NULL, '더미 qna 내용 97
입니다.', '2026-10-23 13:59:58.323341', 0, 'WAITING', '더미글 97', '2026-10-23 13:59:58.323341', 1);
INSERT INTO public.qna VALUES (98, NULL, NULL, '더미 qna 내용 98
입니다.', '2026-10-23 13:59:58.331386', 0, 'WAITING', '더미글 98', '2026-10-23 13:59:58.331386', 1);
INSERT INTO public.qna VALUES (99, NULL, NULL, '더미 qna 내용 99
입니다.', '2026-10-23 13:59:58.337385', 0, 'WAITING', '더미글 99', '2026-10-23 13:59:58.337385', 1);
INSERT INTO public.qna VALUES (100, NULL, NULL, '더미 qna 내용 100
입니다.', '2026-10-23 13:59:58.34591', 0, 'WAITING', '더미글 100', '2026-10-23 13:59:58.34591', 1);
INSERT INTO public.qna VALUES (1, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:02.721235', '더미 qna 내용 1
입니다.', '2026-10-23 13:59:57.468935', 0, 'ANSWERED', '더미글 1', '2026-10-23 14:03:02.763296', 1);
INSERT INTO public.qna VALUES (3, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:02.835731', '더미 qna 내용 3
입니다.', '2026-10-23 13:59:57.599769', 0, 'ANSWERED', '더미글 3', '2026-10-23 14:03:02.840737', 1);
INSERT INTO public.qna VALUES (6, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:02.870327', '더미 qna 내용 6
입니다.', '2026-10-23 13:59:57.623549', 0, 'ANSWERED', '더미글 6', '2026-10-23 14:03:02.877009', 1);
INSERT INTO public.qna VALUES (9, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:02.917522', '더미 qna 내용 9
입니다.', '2026-10-23 13:59:57.650733', 0, 'ANSWERED', '더미글 9', '2026-10-23 14:03:02.921528', 1);
INSERT INTO public.qna VALUES (12, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:02.955566', '더미 qna 내용 12
입니다.', '2026-10-23 13:59:57.676028', 0, 'ANSWERED', '더미글 12', '2026-10-23 14:03:02.959103', 1);
INSERT INTO public.qna VALUES (15, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:02.988138', '더미 qna 내용 15
입니다.', '2026-10-23 13:59:57.701091', 0, 'ANSWERED', '더미글 15', '2026-10-23 14:03:02.992138', 1);
INSERT INTO public.qna VALUES (18, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.020719', '더미 qna 내용 18
입니다.', '2026-10-23 13:59:57.722085', 0, 'ANSWERED', '더미글 18', '2026-10-23 14:03:03.023724', 1);
INSERT INTO public.qna VALUES (21, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.048511', '더미 qna 내용 21
입니다.', '2026-10-23 13:59:57.748105', 0, 'ANSWERED', '더미글 21', '2026-10-23 14:03:03.051507', 1);
INSERT INTO public.qna VALUES (23, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.068601', '더미 qna 내용 23
입니다.', '2026-10-23 13:59:57.764913', 0, 'ANSWERED', '더미글 23', '2026-10-23 14:03:03.071595', 1);
INSERT INTO public.qna VALUES (26, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.094653', '더미 qna 내용 26
입니다.', '2026-10-23 13:59:57.789096', 0, 'ANSWERED', '더미글 26', '2026-10-23 14:03:03.097653', 1);
INSERT INTO public.qna VALUES (29, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.120104', '더미 qna 내용 29
입니다.', '2026-10-23 13:59:57.814472', 0, 'ANSWERED', '더미글 29', '2026-10-23 14:03:03.125027', 1);
INSERT INTO public.qna VALUES (32, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.158361', '더미 qna 내용 32
입니다.', '2026-10-23 13:59:57.838943', 0, 'ANSWERED', '더미글 32', '2026-10-23 14:03:03.161871', 1);
INSERT INTO public.qna VALUES (35, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.18541', '더미 qna 내용 35
입니다.', '2026-10-23 13:59:57.864267', 0, 'ANSWERED', '더미글 35', '2026-10-23 14:03:03.188375', 1);
INSERT INTO public.qna VALUES (38, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.214214', '더미 qna 내용 38
입니다.', '2026-10-23 13:59:57.88912', 0, 'ANSWERED', '더미글 38', '2026-10-23 14:03:03.216225', 1);
INSERT INTO public.qna VALUES (41, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.238439', '더미 qna 내용 41
입니다.', '2026-10-23 13:59:57.913595', 0, 'ANSWERED', '더미글 41', '2026-10-23 14:03:03.241662', 1);
INSERT INTO public.qna VALUES (44, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.26523', '더미 qna 내용 44
입니다.', '2026-10-23 13:59:57.934799', 0, 'ANSWERED', '더미글 44', '2026-10-23 14:03:03.26775', 1);
INSERT INTO public.qna VALUES (46, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.282081', '더미 qna 내용 46
입니다.', '2026-10-23 13:59:57.950496', 0, 'ANSWERED', '더미글 46', '2026-10-23 14:03:03.284091', 1);
INSERT INTO public.qna VALUES (49, '문의하신 사항을 확인했습니다. 감사합니다.', '2026-10-23 14:03:03.306213', '더미 qna 내용 49
입니다.', '2026-10-23 13:59:57.975187', 0, 'ANSWERED', '더미글 49', '2026-10-23 14:03:03.308746', 1);


--
-- TOC entry 5084 (class 0 OID 0)
-- Dependencies: 219
-- Name: bike_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bike_id_seq', 100, true);


--
-- TOC entry 5085 (class 0 OID 0)
-- Dependencies: 221
-- Name: bike_maintenance_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bike_maintenance_id_seq', 100, true);


--
-- TOC entry 5086 (class 0 OID 0)
-- Dependencies: 223
-- Name: datafiles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.datafiles_id_seq', 100, true);


--
-- TOC entry 5087 (class 0 OID 0)
-- Dependencies: 225
-- Name: facility_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.facility_id_seq', 95, true);


--
-- TOC entry 5088 (class 0 OID 0)
-- Dependencies: 227
-- Name: facility_maintenance_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.facility_maintenance_id_seq', 95, true);


--
-- TOC entry 5089 (class 0 OID 0)
-- Dependencies: 229
-- Name: member_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.member_id_seq', 101, true);


--
-- TOC entry 5090 (class 0 OID 0)
-- Dependencies: 231
-- Name: notices_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notices_id_seq', 100, true);


--
-- TOC entry 5091 (class 0 OID 0)
-- Dependencies: 233
-- Name: qna_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.qna_id_seq', 100, true);


-- Completed on 2026-10-23 14:03:48

--
-- PostgreSQL database dump complete
--

\unrestrict cRYchNSJDzM7sgl9gxPsNNLixEG7fj5kBEhRbcFJ5nts3x6QEvpNGX1vVoIe3jv


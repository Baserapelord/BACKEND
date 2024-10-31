# Implementation Notes

### Acerca de la funcionalidad expuesta

Servicio que retorna los datos para la generar el nombre pdf.

Path Parameter = {serie}
Path Parameter = {number}

<br/>
### URI de acceso a la API
| Método | 									   URI 							                   |
|--------|-------------------------------------------------------------------------------------|
| [GET]  | 				  /support/v1.0/document/sga/invoice/file-data 						   |

<br/>
### Precondiciones para el consumo de esta versión de la API
Que se envié de forma correcta el id de la factura

<br/>
### Headers							
| Header | Ejemplo | Obligatorio | 
|--------|---------|-------------|
|	Request-ID 	 |	550e8400-e29b-41d4-a716-446655440000 |  S  |
|	app-code	 |	APP001020 							 |  S  |
|	request-date |	2000-10-31T01:30:00.000-05:00 		 |  S  |


<br/>
### Query Parameters válidos en esta versión de la API 				
No aplica.	

<br/>
### Usos válidos de Query Parameters							
No aplica							

<br/>
### Variantes válidas del Payload (Cuerpo del mesaje)							
No aplica.							
	
<br/>
### Lista de Valores usadas en esta versión de la API							
No aplica.

<br/>
### Código de errores del Servicio en esta versión de la API
| Código  | Status | Descripción                 |
|---------|--------|-----------------------------|
| MVR0001 | 404    | Recurso No encontrado       |
| MVR0002 | 503	   | Tiempo de espera agotado 	 |
| MVR0003 | 500    | Servicio no disponible	     |
| MVR9999 | 500    | Error no controlado	     |

<br/>
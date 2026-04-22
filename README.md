# GreenMind - App de Jardinería Inteligente
**GreenMind** es una aplicación móvil nativa para Android diseñada para ayudar a personas interesadas en la jardinería, especialmente principiantes.

## Características principales

**Autenticación**
- Acceso seguro mediante Google Sign-In con Firebase Authentication.

**Exploración de especies de plantas**
- Listado dinámico y búsqueda de plantas consumiendo la API de Perenual.

**Mi Jardín**
- Gestión de un jardín virtual con acceso offline mediante Room y sincronización en la nube con Firebase Firestore.

**Asistente IA**
- Chat inteligente integrado con Google Gemini para consultas personalizadas sobre botánica.

## Arquitectura y tecnologías

- **Lenguaje**: Kotlin
- **Interfaz**: Jetpack Compose (LazyColumn, manejo de estados)
- **Networking**: Retrofit (consumo de API REST)
- **Imágenes**: Glide (renderizado y optimización)
- **Persistencia**:
   - Room (local)
   - Firebase Firestore (nube)

## Plataforma
- Android - App nativa


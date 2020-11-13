<template>
  <b-container>
    <h4>Student Details</h4>
    <p>Username: {{ student.id }}</p>
    <p>Name: {{ student.name }}</p>
    <p>Email: {{ student.email }}</p>
    <p>Course: {{ student.courseName }}</p>
    <h4>Subjects</h4>
    <b-table
      v-if="subjects.length"
      striped
      over
      :items="subjects"
      :fields="subjectFields"
    />
    <p v-else>
      No subjects enrolled.
    </p>
    <h4>Documents</h4>
    <b-table
      v-if="documents.length"
      striped
      over
      :items="documents"
      :fields="documentsFields"
    >
      <template v-slot:cell(actions)="row">
        <b-btn
          class="btn btn-link"
          target="_blank"
          @click.prevent="download(row.item)"
        >
          Download
        </b-btn>
      </template>
    </b-table>
    <p v-else>
      No documents.
    </p>
    <nuxt-link to="/students">
      Back
    </nuxt-link>
    &nbsp;
    <nuxt-link :to="`/students/${this.id}/send-email`">
      Send e-mail
    </nuxt-link>
    <nuxt-link :to="`/students/upload`">
      Upload
    </nuxt-link>
  </b-container>
</template>
<script>
export default {
  data () {
    return {
      student: {},
      subjectFields: ['code', 'name', 'courseCode', 'courseYear', 'scholarYear'],
      documentsFields: ['filename', 'actions']
    }
  },
  computed: {
    id () {
      return this.$route.params.id
    },
    subjects () {
      return this.student.subjects || []
    },
    documents () {
      return this.student.documents || []
    }
  },
  created () {
    this.$axios.$get(`/api/students/${this.id}`)
      .then((student) => {
        this.student = student || {}
      })
  },
  methods: {
    download (fileToDownload) {
      const documentId = fileToDownload.id
      this.$axios.$get('/api/documents/' + documentId, { responseType: 'arraybuffer' })
        .then((file) => {
          const url = window.URL.createObjectURL(new Blob([file]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', fileToDownload.filename)
          document.body.appendChild(link)
          link.click()
        })
    }
  }
}
</script>

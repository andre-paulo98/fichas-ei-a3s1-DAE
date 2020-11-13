<template>
  <form @submit.prevent="upload">
    <!-- Styled -->
    <b-form-file
      v-model="file"
      :state="hasFile"
      placeholder="Choose a file or drop it here..."
      drop-placeholder="Drop file here..."
    />
    <div class="mt-3">
      Selected file: {{ file ? file.name : '' }}
    </div>
    <b-button type="submit" :disabled="!hasFile">
      Upload
    </b-button>
  </form>
</template>
<script>
export default {
  auth: false,
  data () {
    return {
      file: null
    }
  },
  computed: {
    hasFile () {
      return this.file != null
    },
    formData () {
      const formData = new FormData()
      console.log('1')
      formData.append('id', this.$auth.user.sub)
      console.log('4')
      if (this.file) {
        console.log('2')
        formData.append('file', this.file)
      }
      console.log('5')
      return formData
    }
  },
  methods: {
    upload () {
      if (!this.hasFile) {
        return
      }
      console.log(this.formData)
      this.$axios.$post('/api/documents/upload', this.formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
    }
  }
}
</script>
